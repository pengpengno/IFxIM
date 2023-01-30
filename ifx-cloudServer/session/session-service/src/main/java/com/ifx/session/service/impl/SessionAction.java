package com.ifx.session.service.impl;

import com.ifx.session.service.SessionService;
import com.ifx.session.utils.RedisUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@DubboService
@Service
public class SessionAction {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionLifeStyle sessionLifeStyle;

    @Resource
    private RedisUtil redisUtil;

}
