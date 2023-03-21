package com.ifx.session.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.fegin.AccountApi;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.IdUtil;
import com.ifx.common.utils.ValidatorUtil;
import com.ifx.exec.ex.valid.ValidationException;
import com.ifx.session.entity.BaseEntity;
import com.ifx.session.entity.Session;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.mapstruct.SessionMapper;
import com.ifx.session.repository.SessionAccountRepository;
import com.ifx.session.repository.SessionRepository;
import com.ifx.session.service.ISessionLifeStyle;
import com.ifx.session.vo.session.SessionAccountVo;
import com.ifx.session.vo.session.SessionInfoVo;
import lombok.extern.slf4j.Slf4j;
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
    private AccountApi accountApi;

    @Override
    public Mono<SessionInfoVo> init(String name) {
        return Mono.just(name)
                .map(sessionName ->  {
                    Session session = new Session();

                    session.setSessionName(sessionName);
                    session.setId(IdUtil.getId());
                    return session;
                }).flatMap(l-> r2dbcEntityTemplate.insert(l).map(SessionMapper.INSTANCE::tyrmore));
    }

    private Mono<SessionInfoVo> selectSession(Long sessionId){
        return sessionRepository.findById(sessionId).map(SessionMapper.INSTANCE::tyrmore);
    }

    private Mono<SessionInfoVo> selectSessionWithinCreator(Long sessionId){
        return selectSession(sessionId).map(l-> {
            Long userId = l.getCreateInfo().getUserId();
            l.setCreateInfo(accountApi.getAccountInfo(userId));
            return l;
//            return accountApi.getAccountInfo(userId).map( k-> {
//                l.setCreateInfo(k);
//                return l;
//            });
        });
    }


    @Override
    public Flux<Long> addAccount(SessionAccountVo sessionAccountVo) {
        return Mono.just(sessionAccountVo)
                .doOnNext(l-> ValidatorUtil.validateThrows(sessionAccountVo, SessionAccountVo.SessionAccountAdd.class))
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
