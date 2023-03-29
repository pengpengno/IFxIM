package com.ifx.account.service;


import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.common.base.AccountInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/***
 * 会话生命周期
 */
public interface ISessionLifeStyle {

    Mono<SessionInfoVo> init(String name);


    Flux<Long> addAccount (SessionAccountVo sessionAccountVo);


    Mono<SessionAccountVo>  sessionAccountInfo(Long sessionId);


    public Flux<AccountInfo> checkOnlineUserBySessionId(Long sessionId);







}
