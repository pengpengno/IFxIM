package com.ifx.account.service.impl;

import com.ifx.account.service.reactive.SessionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SessionAction {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionLifeStyle sessionLifeStyle;



}
