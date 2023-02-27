package com.ifx.session.service;

import com.ifx.session.vo.session.SessionInfoVo;
import reactor.core.publisher.Mono;

/**
* @author HP
* @description 针对表【session(会话表)】的数据库操作Service
* @createDate 2022-09-28 16:28:00
*/
public interface SessionService  {


     Mono<Long> post2Session(SessionInfoVo sessionInfoVo);




}
