package com.ifx.session.service.impl;

import com.ifx.account.entity.Account;
import com.ifx.common.utils.CacheUtil;
import com.ifx.session.consts.IFxCommonConstants;
import com.ifx.session.entity.Session;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.service.ISessionLifeStyle;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.service.SessionService;
import com.ifx.session.vo.SessionCreateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@DubboService
@Slf4j
public class SessionLifeStyle implements ISessionLifeStyle {

    @Resource(name = "Memory")
    private CacheUtil cacheUtil;

    @Resource
    private SessionService sessionService;

    @Resource
    private SessionAccountService sessionAccountService;
    @Override
    public Long initialize() {
       log.info("正在创建会话！");
        Long id = sessionService.newSession();
        log.info("成功创建会话 {}！",id);
        return id;
    }


    @Override
    public Long initialize(Set<String> accounts) {
        return null;
    }


    @Override
    public Long create(SessionCreateVo sessionCreateVo) {
        Long aLong = sessionService.newSession();
        Session session = new Session();
        session.setSessionName(sessionCreateVo.getSessionTitle());
        sessionService.save(session);
        SessionAccount sessionAccount = new SessionAccount();
        sessionAccount.setSessionId(aLong);
        sessionAccount.setAccountIds
                (sessionCreateVo.getAccounts().stream()
                        .map(Account::getAccount)
                        .collect(Collectors.joining(IFxCommonConstants.ACCOUNT_SPLIT)));
        sessionAccountService.save(sessionAccount);
        return aLong;
    }

    @Override
    public void start() {

    }

    @Override
    public void hangOn() {
        log.info("正在创建会话！");
        Long id = sessionService.newSession();
        log.info("成功创建会话 {}！",id);
    }



    @Override
    public Boolean reConnect() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean release() {
        return Boolean.TRUE;
    }
}
