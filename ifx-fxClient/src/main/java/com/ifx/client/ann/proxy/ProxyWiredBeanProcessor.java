package com.ifx.client.ann.proxy;

import cn.hutool.core.util.TypeUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.ifx.client.ann.ProxyService;
import javafx.util.Callback;

import java.lang.reflect.Field;

/**
 *
 * 代理注入执行器
 * @author pengpeng
 * @description
 * @date 2023/2/5
 */
@Singleton
public class ProxyWiredBeanProcessor {

    /***
     * 代理 bean 实现
     * @return
     */
    public  Callback<Class<?>, Object> proxyBeanProcessor(){
        Injector injector = Guice.createInjector();
        return (tClass)-> {
            Object instance = injector.getInstance(tClass);
            Field[] fields = tClass.getDeclaredFields();
            for (Field f : fields){
                ProxyService annotation = f.getAnnotation(ProxyService.class);
                if (annotation != null){
                    Object proxy = ProxyUtil.generateService(TypeUtil.getClass(f));
                    f.setAccessible(true);
                    try {
                        f.set(instance , proxy);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return instance;
        };

    }
}
