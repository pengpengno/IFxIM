package com.ifx.account.service.reactive;

import com.ifx.account.vo.session.SessionInfoVo;
import reactor.core.publisher.Mono;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service
* @createDate 2022-09-28 16:28:00
*/
public interface SessionService  {


     /**
      * 创建/更新 session
      * @param sessionInfoVo
      * @return
      */
     Mono<Long> post2Session(SessionInfoVo sessionInfoVo);

     public Mono<SessionInfoVo> selectSession(Long sessionId);

     Mono<SessionInfoVo> selectSessionWithinCreator(Long sessionId);



}
