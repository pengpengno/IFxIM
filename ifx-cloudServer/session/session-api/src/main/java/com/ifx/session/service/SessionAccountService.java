package com.ifx.session.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ifx.session.entity.SessionAccount;

import java.util.Set;

/**
* @author HP
* @description 针对表【session_account(会话账户中间表)】的数据库操作Service
* @createDate 2022-09-28 16:35:38
*/
public interface SessionAccountService extends IService<SessionAccount> {

    /***
     * h
     * @param sessionId
     * @return
     */
    Set<String> listAccBySessionId(Long sessionId);




}
