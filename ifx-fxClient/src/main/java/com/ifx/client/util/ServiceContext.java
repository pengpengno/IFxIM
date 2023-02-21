package com.ifx.client.util;

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端服务上下文
 * @author pengpeng
 * @description
 * @date 2023/1/30
 */
@Slf4j
@Singleton
public class ServiceContext {

    private static ConcurrentHashMap<Object , Object>  serviceContext ;


    private enum INSTANCE{
        INSTANCE;
        public final ServiceContext instance ;

        INSTANCE(){
            instance = new ServiceContext();
            serviceContext = new ConcurrentHashMap<>();
        }
        public static ServiceContext getInstance(){
            return INSTANCE.instance;
        }
    }
    public static ServiceContext getInstance(){
        return ServiceContext.INSTANCE.getInstance();
    }
    public void s(){

    }

    /***
     * 获取服务
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T getService(Class<T> tClass){
        return  null;
    }
}
