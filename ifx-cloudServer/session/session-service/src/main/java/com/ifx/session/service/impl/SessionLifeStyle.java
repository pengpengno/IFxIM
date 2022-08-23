package com.ifx.session.service.impl;

import com.ifx.session.service.ISessionLifeStyle;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class SessionLifeStyle implements ISessionLifeStyle {
    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void add() {

    }

    @Override
    public void hangOn() {

    }

    @Override
    public void reConnect() {

    }

    @Override
    public void release() {

    }
}
