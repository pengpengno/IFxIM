package com.ifx.connect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Slf4j
public class Proxy implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("doing proxy");
//        proxy.invoke(obj,args);

        Type genericReturnType = method.getGenericReturnType();

//        ReflectUtil.invoke()
//        genericReturnType

        log.info("after proxy");
        return 11l;
    }
}
