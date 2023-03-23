package com.ifx.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.entity.BaseEntity;
import com.ifx.account.entity.Session;
import com.ifx.account.entity.SessionAccount;
import com.ifx.account.mapstruct.SessionMapper;
import com.ifx.account.repository.SessionAccountRepository;
import com.ifx.account.repository.SessionRepository;
import com.ifx.account.service.ISessionLifeStyle;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.IdUtil;
import com.ifx.common.utils.ValidatorUtil;
import com.ifx.exec.ex.valid.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    private Mono<SessionInfoVo> selectSession(Long sessionId){
        return sessionRepository.findById(sessionId).map(SessionMapper.INSTANCE::session2Vo);
    }

    private Mono<SessionInfoVo> selectSessionWithinCreator(Long sessionId){
        return selectSession(sessionId).flatMap(l-> {
            Long userId = l.getCreateInfo().getUserId();
           return accountService.findByUserId(userId).map(e-> {
               l.setCreateInfo(e);
               return l;
           });
        });
    }


    @Override
    public Flux<Long> addAccount(SessionAccountVo sessionAccountVo) {
        return Mono.just(sessionAccountVo)
                .doOnNext(l-> ValidatorUtil.validateThrows(sessionAccountVo, SessionAccountVo.SessionAccountAdd.class))
                .map(vo -> {
                    // take the
                    sessionAccountInfo(vo.getSessionId()).doOnNext(info-> {
                        Set<Long> existsAccounts = info.getAddUseIdSet();
                        Set<Long> notExistsId = vo.getAddUseIdSet().stream().filter(id -> existsAccounts.contains(id)).collect(Collectors.toSet());
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
    public Mono<SessionAccountVo> sessionAccountInfo(Long sessionId) {
        Message message = new Message();
        rabbitTemplate.sendAndReceive(Message)

        return sessionAccountRepository.queryGroupBySessionId(sessionId)
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
                    ).zipWith(selectSessionWithinCreator(sessionId),(w1,w2) -> {
                        w1.setSessionName(w2.getSessionName());
                        w1.setCreateInfo(w2.getCreateInfo());
                        return w1;
                });

        }


}
