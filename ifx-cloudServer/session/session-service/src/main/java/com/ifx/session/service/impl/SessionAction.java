package com.ifx.session.service.impl;

import com.ifx.session.service.ISessionAction;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class SessionAction implements ISessionAction {
    @Override
    public void flush2Db() {

    }

    @Override
    public void notifyAllAcc() {

    }
}
