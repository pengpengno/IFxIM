package com.ifx.account.service;

import com.ifx.account.vo.session.SessionAccountContextVo;
import com.ifx.account.vo.session.SessionAccountVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/28
 */
public interface ISessionAccountService {



    Mono<SessionAccountVo>  sessionAccount(Long sessionId);


    Flux<Long> addAccount(SessionAccountVo sessionAccountVo);


    public Mono<SessionAccountContextVo> sessionAccContextVo(Long sessionId);

}
