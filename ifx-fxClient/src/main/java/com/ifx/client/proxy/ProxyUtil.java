package com.ifx.client.proxy;

import java.util.concurrent.ConcurrentHashMap;

public class ProxyUtil {


    private static ConcurrentHashMap<Class<?>,Object> proxyMap ;



    public static <T> T getProxy(Class<T> tClass){
        initProxyMap();
        return (T)proxyMap.computeIfAbsent(tClass,(k)->ProxyBean.getProxyBean(tClass));
    }



    public static void initProxyMap(){
        if (proxyMap == null){
            proxyMap = new ConcurrentHashMap<>();
        }
    }

    private enum INSTANCE{
        INSTANCE;
        public final ProxyUtil instance ;
        INSTANCE(){
            instance = new ProxyUtil();
        }
        public static ProxyUtil getInstance(){
            return INSTANCE.instance;
        }
    }
    public static ProxyUtil getInstance(){
        return ProxyUtil.INSTANCE.getInstance();
    }



}
