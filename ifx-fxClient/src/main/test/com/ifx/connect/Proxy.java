package com.ifx.connect;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.connect.proto.parse.DubboGenericParse;
import com.ifx.client.service.ClientService;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Slf4j
public class Proxy implements MethodInterceptor {

    @Resource
    private ClientService clientService;

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
        log.info("currently thread {} doing proxy {}",Thread.currentThread().getName(),obj.getClass().getName());

        Method getFastClass = ReflectUtil.getMethod(proxy.getClass(), "getFastClass");
        getFastClass.setAccessible(true);
        FastClass invoke = ReflectUtil.invoke(proxy, getFastClass);
        DubboApiMetaData metaData = DubboGenericParse.applyMeta0(invoke.getJavaClass(), method, args);
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        protocol.setType(IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER);
//        clientService.send(protocol);
        Class<?> returnType = method.getReturnType();
        method.setAccessible(true);
//        Object cl = Protocol.class;
//        proxy.getClass().getName()
        ReflectUtil.setFieldValue(method,"returnType",Protocol.class);
//        proxy.
        log.info("after proxy");
        return protocol;
    }
}
