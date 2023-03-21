package com.ifx.session.service;


import com.ifx.session.vo.session.SessionAccountVo;
import com.ifx.session.vo.session.SessionInfoVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/***
 * 会话生命周期
 */
public interface ISessionLifeStyle {

    Mono<SessionInfoVo> init(String name);


    Flux<Long> addAccount (SessionAccountVo sessionAccountVo);


    Mono<SessionAccountVo>  sessionAccountInfo(Long sessionId);







}
