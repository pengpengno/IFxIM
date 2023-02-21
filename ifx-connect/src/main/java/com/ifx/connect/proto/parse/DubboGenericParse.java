package com.ifx.connect.proto.parse;

import com.alibaba.fastjson2.JSON;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Component
public class DubboGenericParse {


    /**
     * ！！！！ 此处传入的 args 必须严格按照接口签名的参数顺序注入
     * 上面的接口只有一个参数 那么在注入 args 的时候就需要 做如下操作
     * AccountBaseInfo accBaseInfo = new AccountBaseInfo()
     * List<Object>
     * @see DubboGenericParse#applyMeta0(java.lang.Class, java.lang.reflect.Method, java.lang.Object...)
     * @param method
     * @param arg
     * @return
     */
    @SneakyThrows
    public static DubboApiMetaData applyMeta( Method method , Object... arg){
        String name = method.getDeclaringClass().getName();
        Object[] args = Arrays.stream(arg).toArray();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = Arrays.stream(parameterTypes).
                map(Class::getName).
                toArray(String[]::new);
        DubboApiMetaData metaData = new DubboApiMetaData();
        metaData.setApiInterFacePath(name);
        metaData.setArgsType(paramTypes);
        metaData.setArgs(args);
        metaData.setMethod(method.getName());
        return metaData;
    }

//
//    @SneakyThrows
//    public static DubboApiMetaData applyMeta(Class<?> interFaceClass, String methodName ,Object[] args){
//        String name = interFaceClass.getName();
//        Class<?>[] classes = new Class<?>[args.length];
//        for (int i = 0; i < args.length; i++) {
//            classes[i] = args[i].getClass();
//        }
//        Method method = interFaceClass.getMethod(methodName, classes);
//        Class<?>[] parameterTypes = method.getParameterTypes();
//        String[] paramTypes = Arrays.stream(parameterTypes)
//                .map(Class::getName)
//                .toArray(String[]::new);
//        DubboApiMetaData metaData = new DubboApiMetaData();
//        metaData.setApiInterFacePath(name);
//        metaData.setArgsType(paramTypes);
//        metaData.setArgs(args);
//        metaData.setMethod(methodName);
//        return metaData;
//    }

    /**
     * 生成 基于 dubbo 的元数据
     * @param interFaceClass 接口类
     * @param method 接口方法
     * @param arg
     * @return
     */
    @SneakyThrows
    public static DubboApiMetaData applyMeta0(Class<?> interFaceClass, Method method ,Object... arg){
        String name = interFaceClass.getName();
        Object[] args = Arrays.stream(arg).toArray();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = Arrays.stream(parameterTypes).
                map(Class::getName).
                toArray(String[]::new);
        DubboApiMetaData metaData = new DubboApiMetaData();
        metaData.setApiInterFacePath(name);
        metaData.setArgsType(paramTypes);
        metaData.setArgs(args);
        metaData.setMethod(method.getName());
        return metaData;
    }


    /***
     * 组装 客户端基本消息协议
     * @see IFxMsgProtocol
     * @code {
     *     IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER
     * }
     * @param method
     * @param arg
     * @return
     */
    public static Protocol applyMsgProtocol( Method method ,Object... arg){
        return applyProtocol(IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER,method,arg);
    }

    /**
     * 提供协议
     * @param method
     * @param arg
     * @param headerType 协议类型 {@see IFxMsgProtocol}
     * @see IFxMsgProtocol
     * @return
     */
    public static Protocol applyProtocol( String headerType, Method method , Object... arg){
        DubboApiMetaData metaData = applyMeta(method, arg);
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        protocol.setType(headerType);
        return protocol;
    }


}
