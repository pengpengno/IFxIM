package com.ifx.session.service;


import com.ifx.session.entity.SessionAccount;
import com.ifx.session.vo.session.SessionAccountVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
* @author HP
* @description 针对表【session_account(会话账户中间表)】的数据库操作Service
* @createDate 2022-09-28 16:35:38
*/
public interface SessionAccountService  {

    /***
     * 查询会话下所有的用户id
     * @param sessionId
     * @return
     */
    Flux<String> listAccBySessionId(Long sessionId);


    /***
     * 添加或创建会话
     * @param sessionAccountVo
     * @return
     */
    Mono<SessionAccount> addAcc2Session(SessionAccountVo sessionAccountVo);



}
