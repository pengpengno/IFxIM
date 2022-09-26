package com.ifx.session.service;

import com.ifx.account.entity.Account;
//import com.ifx.session.entity.SessionAccount;
//import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author
* @description 针对表【session_account(会话账户中间表)】的数据库操作Service
* @createDate 2022-09-06 11:51:33
*/
public interface SessionAccountService {

    public List<Account> listSessionAcc(Long sessionId);

    public List<String> listSessionAccs(Long sessionId) ;


}
