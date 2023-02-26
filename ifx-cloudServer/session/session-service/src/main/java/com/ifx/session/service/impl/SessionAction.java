package com.ifx.session.service.impl;

import com.ifx.session.service.SessionService;
import com.ifx.session.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

//@DubboService
@Service
public class SessionAction {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionLifeStyle sessionLifeStyle;

    @Resource
    private RedisUtil redisUtil;

}
