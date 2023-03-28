package com.ifx.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.account.entity.BaseEntity;
import com.ifx.account.entity.Session;
import com.ifx.account.entity.SessionAccount;
import com.ifx.account.mapstruct.SessionMapper;
import com.ifx.account.repository.SessionAccountRepository;
import com.ifx.account.repository.SessionRepository;
import com.ifx.account.service.ISessionAccountService;
import com.ifx.account.service.ISessionLifeStyle;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.IdUtil;
import com.ifx.common.utils.ValidatorUtil;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.OnLineUser;
import com.ifx.exec.ex.valid.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RpcClient;
import reactor.rabbitmq.Sender;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SessionLifeStyle implements ISessionLifeStyle {
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private SessionAccountRepository sessionAccountRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ReactiveAccountService accountService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ISessionAccountService sessionAccountService;

    @Autowired
    private Sender sender;

    @Value("${online.user.search.queue:online.user.search}")
    private String onlineQueue ;

    @Value("${online.user.routeKey:online.user.search}")
    private String onlineUserRouteKey;


    @Override
    public Mono<SessionInfoVo> init(String name) {
        return Mono.just(name)
                .map(sessionName ->  {
                    Session session = new Session();
                    session.setSessionName(sessionName);
                    session.setId(IdUtil.getId());
                    return session;
                }).flatMap(l-> r2dbcEntityTemplate.insert(l).map(SessionMapper.INSTANCE::session2Vo));
    }



    private Flux<SessionInfoVo> selectSessionInfos(Iterable<Long> sessionIds) {
        return sessionRepository.findAllById(sessionIds).map(SessionMapper.INSTANCE::session2Vo);
    }




    @Override
    public Flux<Long> addAccount(SessionAccountVo sessionAccountVo) {
        return sessionAccountService.addAccount(sessionAccountVo);
    }


    @Override
    public Flux<AccountInfo> checkoutUserOnlineStatusBySessionId(Iterable<Long> sessionId) {

        ArrayList<AccountInfo> accInfos = CollectionUtil.newArrayList();

        Flux<SessionInfoVo> sessionInfoVoFlux = Mono.justOrEmpty(Optional.ofNullable(sessionId))
                .flatMapMany(this::selectSessionInfos)
               .reduce(accInfos, (accountInfos , sessions)->{  } )

        ;
        return null;
    }


    @Override
    public Mono<SessionAccountVo> sessionAccountInfo(Long sessionId) {
        return sessionAccountService.sessionAccount(sessionId);
    }




    /***
     * 检查线上用户状态
     * @param accountInfos
     * @return
     */
    private List<AccountInfo> checkoutUserOnlineStatus(List<AccountInfo> accountInfos){
        OnLineUser.UserSearch userSearch = SessionMapper.INSTANCE.buildSearch(accountInfos);
        Message message = new Message(userSearch.toByteArray());
        Message returnMessage = rabbitTemplate.sendAndReceive(onlineUserRouteKey,message, new CorrelationData());
        byte[] body = returnMessage.getBody();
        try {
            OnLineUser.UserSearch returnSearch = OnLineUser.UserSearch.parseFrom(body);
            List<Account.AccountInfo> accountsList = returnSearch.getAccountsList();
            return ProtoBufMapper.INSTANCE.proto2AccIterable(accountsList);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("格式异常！");
        }
    }


    /***
     * 检查用户上线状态
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
