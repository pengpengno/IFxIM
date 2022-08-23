package com.ifx.session.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ifx.session.entity.Session;
import com.ifx.session.mapper.SessionMapper;
import com.ifx.session.service.IChatAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service
@Slf4j
public class ChatAction implements IChatAction {

    @Autowired
    SessionServiceImpl sessionService;
    @Autowired
    SessionMapper sessionMapper;

    @Override
    public void pushMsg(String fromAccount, String sessionId, String msg) {
        QueryWrapper<Session> session_id = new QueryWrapper<Session>().eq("session_id", sessionId);
        Session one = sessionService.getOne(session_id);


    }

    @Override
    public void pullMsg(String fromAccount, String session) {

    }

    @Override
    public void pullHisMsg(String session) {

    }

}
