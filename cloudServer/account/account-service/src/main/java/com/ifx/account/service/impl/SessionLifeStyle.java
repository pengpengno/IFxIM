package com.ifx.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.entity.Session;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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
    private ISessionAccountService sessionAccountService;


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


    public Flux<AccountInfo> checkoutUserOnlineStatusBySessionId(Iterable<Long> sessionId) {

        ArrayList<AccountInfo> accInfos = CollectionUtil.newArrayList();

//        Flux<SessionInfoVo> sessionInfoVoFlux = Mono.justOrEmpty(Optional.ofNullable(sessionId))
//                .flatMapMany(this::selectSessionInfos)
//               .reduce(accInfos, (accountInfos , sessions)->{  } )
//
//        ;
        return null;
    }

    /***
     * 查询在线用户
     * @param sessionId
     * @return 返回在线用户信息
     */
    public Flux<AccountInfo> checkOnlineUserBySessionId(Long sessionId){
        return sessionAccountService.checkoutUserOnlineBySession(sessionId).flatMapMany(e-> Flux.fromIterable(e));
    }

    @Override
    public List<AccountInfo> checkOnlineUserListBySessionId(Long sessionId) {
        return sessionAccountService.checkoutUserOnlineBySession(sessionId).onErrorReturn(CollectionUtil.newArrayList()).block();
    }

    @Override
    public Mono<SessionAccountVo> sessionAccountInfo(Long sessionId) {
        return sessionAccountService.sessionAccount(sessionId);
    }


    @Override
    public Flux<SessionInfoVo> findSessionInfoByUserId(Long userId) {
        return sessionAccountService.findSessionByUserId(userId);
    }
}
