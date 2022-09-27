package com.ifx.connect;

import com.ifx.session.entity.Session;
import com.ifx.session.service.SessionService;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;

public class CglibTest {
    @Test
    public void cglibTest(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SessionService.class);
        enhancer.setCallback(new Proxy());
        SessionService sessionService = (SessionService) enhancer.create();
        sessionService.newSession();

    }
}
