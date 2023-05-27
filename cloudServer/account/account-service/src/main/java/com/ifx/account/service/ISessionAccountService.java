package com.ifx.account.service;

import com.ifx.account.vo.session.SessionAccountContextVo;
import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.common.base.AccountInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/28
 */
public interface ISessionAccountService {



    Mono<SessionAccountVo>  sessionAccount(Long sessionId);


    /**
     * 查询用户下的所有 Session 的信息
     * @param userId
     * @return
     */
    Flux<SessionInfoVo>  findSessionByUserId(Long userId);



    Flux<Long> addAccount(SessionAccountVo sessionAccountVo);


    public Mono<SessionAccountContextVo> sessionAccContextVo(Long sessionId);



    /***
     * 查询用户在线状态
     * @param sessionId
     * @return
     */
    public Mono<List<AccountInfo>> checkoutUserOnlineBySession(Long  sessionId);


}
