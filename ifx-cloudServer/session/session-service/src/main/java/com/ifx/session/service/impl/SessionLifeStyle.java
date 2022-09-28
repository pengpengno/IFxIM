package com.ifx.session.service.impl;

import com.ifx.common.utils.CacheUtil;
import com.ifx.session.service.ISessionLifeStyle;
import com.ifx.session.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
@DubboService
@Slf4j
public class SessionLifeStyle implements ISessionLifeStyle {

    @Resource(name = "Memory")
    private CacheUtil cacheUtil;

    @Resource
    private SessionService sessionService;
    @Override
    public Long initialize() {
       log.info("正在创建会话！");
        Long id = sessionService.newSession();
        log.info("成功创建会话 {}！",id);
        return id;
    }


    @Override
    public Long initialize(Set<String> accounts) {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void hangOn() {
        log.info("正在创建会话！");
        Long id = sessionService.newSession();
        log.info("成功创建会话 {}！",id);
    }



    @Override
    public Boolean reConnect() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean release() {
        return Boolean.TRUE;
    }
}
