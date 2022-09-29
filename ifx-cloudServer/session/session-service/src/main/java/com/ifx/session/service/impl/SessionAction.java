package com.ifx.session.service.impl;

import com.ifx.common.base.AccountInfo;
import com.ifx.session.service.ISessionAction;
import com.ifx.session.service.SessionService;
import com.ifx.session.utils.RedisUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@DubboService
@Service
public class SessionAction implements ISessionAction {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionLifeStyle sessionLifeStyle;

    @Resource
    private RedisUtil redisUtil;

    public void flush2Db() {

    }

    public void add() {

    }

    public void addAcc(Long sessionId, Set<AccountInfo> accountInfo) {

    }


    @Override
    public Long addAcc(Set<String> accounts) {

        return null;
    }

    public void notifyAllAcc() {

    }
}
