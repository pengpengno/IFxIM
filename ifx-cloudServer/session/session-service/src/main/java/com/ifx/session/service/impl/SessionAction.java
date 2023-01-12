package com.ifx.session.service.impl;

import com.ifx.common.base.AccountInfo;
import com.ifx.exec.BaseException;
import com.ifx.exec.ex.bus.IFXException;
import com.ifx.exec.ex.bus.session.SessionException;
import com.ifx.session.entity.Session;
import com.ifx.session.service.ISessionAction;
import com.ifx.session.service.SessionService;
import com.ifx.session.utils.RedisUtil;
import com.ifx.session.vo.SessionMsgVo;
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

    @Override
    public Long addSessionMsg(SessionMsgVo sessionMsgVo) {
        Long sessionId = sessionMsgVo.getSessionId();
        Session session = sessionService.getSession(sessionId);
        if (session == null){
            throw  new SessionException("查询不到当前会话");
        }
        return null;
    }

    public void addAcc(Long sessionId, Set<AccountInfo> accountInfo) {

    }

    @Override
    public void flush2Db(Long sessionId) {

    }

    @Override
    public Long addAcc(Set<String> accounts) {

        return null;
    }

    public void notifyAllAcc() {

    }
}
