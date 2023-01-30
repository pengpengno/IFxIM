package com.ifx.client.util;

import com.ifx.client.app.pane.SearchPane;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/30
 */
@Slf4j
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

    public static <T> T getService(Class<T> tClass){

    }
}
