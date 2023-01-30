package com.ifx.connect;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import com.ifx.connect.proto.parse.DubboGenericParse;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Method;

/***
 * 客户端服务调用实现类
 */
@Slf4j
public class IFxDubboProtocolProxy implements MethodInterceptor {




    /**
     * 动态代理接口方法,避免手动序列化
     * 1. 获取动态代理类
     * 2. 组装传输 protocol
     * 3. 发送请求报文
     * 4. 线程等待 res
     * 5. 返回 结果
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)  {
        log.debug("currently thread {} doing proxy {}",Thread.currentThread().getName(),obj.getClass().getName());
        Method getFastClass = ReflectUtil.getMethod(proxy.getClass(), "getFastClass");
        getFastClass.setAccessible(true);
        FastClass invoke = ReflectUtil.invoke(proxy, getFastClass);
        Protocol protocol = DubboGenericParse.applyMsgProtocol( method, args);
        ClientToolkit.getDefaultClientAction().sendJsonMsg(protocol);

        Class<?> returnType = method.getReturnType();
        method.setAccessible(true);

        ReflectUtil.setFieldValue(method,"returnType",Protocol.class);
//        proxy.
        log.info("after proxy");
        return protocol;
    }
}
