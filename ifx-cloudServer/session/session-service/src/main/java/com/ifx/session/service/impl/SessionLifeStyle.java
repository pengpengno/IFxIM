package com.ifx.session.service.impl;

import com.ifx.common.utils.CacheUtil;
import com.ifx.session.service.ISessionLifeStyle;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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



}
