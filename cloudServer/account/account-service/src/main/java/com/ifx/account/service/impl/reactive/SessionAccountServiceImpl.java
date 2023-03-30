package com.ifx.account.service.impl.reactive;

import cn.hutool.core.collection.CollectionUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.account.entity.BaseEntity;
import com.ifx.account.entity.SessionAccount;
import com.ifx.account.mapstruct.SessionMapper;
import com.ifx.account.repository.SessionAccountRepository;
import com.ifx.account.repository.SessionRepository;
import com.ifx.account.service.ISessionAccountService;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.service.reactive.SessionService;
import com.ifx.account.vo.session.SessionAccountContextVo;
import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.OnLineUser;
import com.ifx.exec.BaseException;
import com.ifx.exec.ex.valid.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RpcClient;
import reactor.rabbitmq.Sender;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/28
 */
@Service
@Slf4j
public class SessionAccountServiceImpl implements ISessionAccountService {

    @Autowired
    private SessionAccountRepository sessionAccountRepository;

    @Autowired
    private ReactiveAccountService accountService;

    @Autowired
    private SessionService sessionService;


    @Value("${online.user.search.queue:online.user.search}")
    private String onlineQueue ;

    @Value("${online.user.routeKey:online.user.search}")
    private String onlineUserRouteKey;


    @Autowired
    private Sender sender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Mono<SessionAccountVo> sessionAccount(Long sessionId) {
        return sessionAccountRepository.queryBySessionId(sessionId)
                .reduceWith(()-> {
                            SessionAccountVo vo = new SessionAccountVo();
                            vo.setAddUseIdSet(CollectionUtil.newHashSet());
                            vo.setSessionId(sessionId);
                            return vo;
                        },(vo, sessionAccount)-> {
                            Long userId = sessionAccount.getUserId();
                            vo.getAddUseIdSet().add(userId);
                            return vo;
                        }
                ).zipWith(sessionService.selectSessionWithinCreator(sessionId),(w1,w2) -> {
                    w1.setSessionName(w2.getSessionName());
                    w1.setCreateInfo(w2.getCreateInfo());
                    return w1;
                });
    }

    public Flux<Long> addAccount(SessionAccountVo sessionAccountVo) {
        return Mono.just(sessionAccountVo)
                .doOnNext(l-> ValidatorUtil.validateThrows(sessionAccountVo, SessionAccountVo.SessionAccountAdd.class))
                .map(vo -> {
                    // filter the  user which is not exists;
                    sessionAccount(vo.getSessionId()).doOnNext(info-> {
                        Set<Long> existsAccounts = info.getAddUseIdSet();
                        Set<Long> notExistsId = vo.getAddUseIdSet().stream().filter(existsAccounts::contains).collect(Collectors.toSet());
                        vo.setAddUseIdSet(notExistsId);
                    });
                    return vo;
                })
                .doOnNext(vo -> {
                    Long sessionId = vo.getSessionId();
                    sessionRepository.findById(sessionId).hasElement().doOnNext(exist -> {
                        if (!exist){
                            throw new ValidationException("The specify session is not exists!");
                        }
                    });
                })
                .map(vo-> {
                    AccountInfo createInfo = vo.getCreateInfo();
                    Long sessionId = vo.getSessionId();
                    Long userId = createInfo.userId();
                    Set<Long> addAccSet = vo.getAddUseIdSet();
                    return addAccSet.stream().map(e -> {
                        SessionAccount sessionAccount = SessionAccount.builder().sessionId(sessionId)
                                .userId(e)
                                .build();
                        sessionAccount.setCreateUserId(userId);
                        return sessionAccount;
                    }).collect(Collectors.toList());
                })
                .flatMapMany(elements -> sessionAccountRepository.saveAll(elements).map(BaseEntity::getId));
    }

    @Override
    public Mono<List<AccountInfo>> checkoutUserOnlineBySession(Long sessionId) {
       return sessionAccContextVo(sessionId)
                .flatMap(vo -> {
                    Map<Long, AccountInfo> context = vo.getSessionAccountContext();
                    if (CollectionUtil.isEmpty(context)){
                        return Mono.just(context.values());
                    }
                    return Mono.empty();
                })
                .map(e-> checkoutUserOnlineStatus(e));

    }

    public Mono<SessionAccountContextVo> sessionAccContextVo(Long sessionId) {
        Assert.notNull(sessionId,"The specify session could not be null!");
        return sessionAccountRepository.queryBySessionId(sessionId)
                .reduceWith(() -> {
                    SessionAccountContextVo sessionAccountVo = new SessionAccountContextVo();
                    sessionAccountVo.setSessionAccountContext(new HashMap<>());
                    return sessionAccountVo;
                }, (vo, sessionAcc) -> {
                    Map<Long, AccountInfo> context = vo.getSessionAccountContext();
                    context.keySet().add(sessionAcc.getUserId());
                    return vo;
                }).flatMap(e -> {
                    Map<Long, AccountInfo> context = e.getSessionAccountContext();
                    Set<Long> userIds = context.keySet();
                    return accountService.findByUserIds(userIds)
                            .doOnNext(info -> context.put(info.getUserId(), info))
                            .then().flatMap(l -> Mono.just(e));
                });
    }





    /***
     * 检查用户上线状态
     * @param accountInfos 用户信息
     * @return 用户信息
     */
    public List<AccountInfo> checkoutUserOnlineStatus(Iterable<AccountInfo> accountInfos){
        OnLineUser.UserSearch userSearch = SessionMapper.INSTANCE.buildSearch(accountInfos);
        Message message = new Message(userSearch.toByteArray());
        Message returnMessage ;
        try{
             returnMessage = rabbitTemplate.sendAndReceive(onlineUserRouteKey,message, new CorrelationData());
        }catch (AmqpException amqpException){
            throw new BaseException("Check online user error!");
        }
        Assert.notNull(returnMessage,"Check online user error!");
        byte[] body = returnMessage.getBody();
        try {
            OnLineUser.UserSearch returnSearch = OnLineUser.UserSearch.parseFrom(body);
            List<Account.AccountInfo> accountsList = returnSearch.getAccountsList();
            return ProtoBufMapper.INSTANCE.proto2AccIterable(accountsList);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("格式异常！");
        }
    }



    public Mono<SessionAccountVo> sessionAccountVoMono(Long sessionId) {

        return sessionAccountRepository.queryBySessionId(sessionId)
                .reduceWith(()-> {
                            SessionAccountVo vo = new SessionAccountVo();
                            vo.setAddUseIdSet(CollectionUtil.newHashSet());
                            vo.setSessionId(sessionId);
                            return vo;
                        },(vo, sessionAccount)-> {
                            Long userId = sessionAccount.getUserId();
                            vo.getAddUseIdSet().add(userId);
                            return vo;
                        }
                ).zipWith(sessionService.selectSessionWithinCreator(sessionId),(w1,w2) -> {
                    w1.setSessionName(w2.getSessionName());
                    w1.setCreateInfo(w2.getCreateInfo());
                    return w1;
                });
    }




    /***
     * 检查用户上线状态 reactor-rabbitmq 实现
     * @param accountInfos
     * @return
     */
    public Flux<AccountInfo> checkoutUserOnlineStatusReactor(List<AccountInfo> accountInfos){
        return Mono.just(accountInfos)
                .map(SessionMapper.INSTANCE::buildSearch)
                .flatMap(l -> sender.rpcClient("",onlineQueue).rpc(Mono.just(new RpcClient.RpcRequest(l.toByteArray()))))
                .map(res -> {
                    byte[] body = res.getBody();
                    try {
                        return OnLineUser.UserSearch.parseFrom(body);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("格式异常！");
                    }
                })
                .flatMapMany(userSearch -> {
                    List<Account.AccountInfo> accountsList = userSearch.getAccountsList();
                    return Flux.fromIterable(accountsList)
                            .map(ProtoBufMapper.INSTANCE::proto2Acc);
                });
    }

}
