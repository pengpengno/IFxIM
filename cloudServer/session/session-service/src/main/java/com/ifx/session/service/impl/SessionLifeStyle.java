package com.ifx.session.service.impl;

import com.ifx.session.service.ISessionLifeStyle;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.service.SessionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
//@DubboService
@Slf4j
public class SessionLifeStyle implements ISessionLifeStyle {

//
    @Resource
    private SessionService sessionService;

    @Resource
    private SessionAccountService sessionAccountService;



}
