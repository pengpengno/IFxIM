package com.ifx.account.service.impl.reactive;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.entity.BaseEntity;
import com.ifx.account.entity.SessionAccount;
import com.ifx.account.repository.SessionAccountRepository;
import com.ifx.account.repository.SessionRepository;
import com.ifx.account.service.ISessionAccountService;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.service.reactive.SessionService;
import com.ifx.account.vo.session.SessionAccountContextVo;
import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import com.ifx.exec.ex.valid.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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



    public Mono<SessionAccountContextVo> sessionAccContextVo(Long sessionId) {
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
}
