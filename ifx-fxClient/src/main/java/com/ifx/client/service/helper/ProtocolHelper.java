package com.ifx.client.service.helper;

import com.alibaba.fastjson2.JSON;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProtocolHelper {


    /**
     * 废弃 请使用下方接口实现
     * {@link ProtocolHelper}
     * @see ProtocolHelper
     * @param interFaceClass 接口
     * @param methodName 方法
     * @param args 参数
     * @return
     */
    @SneakyThrows
    public static Protocol applyDubboProtocol(Class<?> interFaceClass, String methodName ,Object[] args){
        String name = interFaceClass.getName();
//        if (args!=null){
//
//        }
        Class<?>[] classes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        Method method = interFaceClass.getMethod(methodName, classes);
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = Arrays.stream(parameterTypes)
                .map(Class::getName)
                .toArray(String[]::new);
        DubboApiMetaData metaData = new DubboApiMetaData();
        metaData.setApiInterFacePath(name);
        metaData.setArgsType(paramTypes);
        metaData.setArgs(args);
        metaData.setMethod(methodName);
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        return protocol;
    }

    public static void main(String[] args) throws NoSuchMethodException {

        Method search = com.ifx.account.service.AccountService.class.getMethod("search", AccountSearchVo.class);
        search.getDeclaringClass();
    }
}
