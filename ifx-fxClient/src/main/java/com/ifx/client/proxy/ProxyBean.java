package com.ifx.client.proxy;

import com.ifx.session.service.SessionService;
import net.sf.cglib.proxy.Enhancer;

public class ProxyBean {
    @SuppressWarnings(value = "all")
    public static <T> T getProxyBean(Class<T> tClass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(ClientApiProxy.getInstance());
        return (T) enhancer.create();
    }
}
