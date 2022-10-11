package com.ifx.client.parse;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DubboGenericParse {


    /**
     * ！！！！ 此处传入的 args 必须严格按照接口签名的参数顺序注入
     * e.g
     * @see AccountService#login(AccountBaseInfo)
     * 上面的接口只有一个参数 那么在注入 args 的时候就需要 做如下操作
     * AccountBaseInfo accBaseInfo = new AccountBaseInfo()
     * List<Object>
     * @see DubboGenericParse#applyMeta0(java.lang.Class, java.lang.reflect.Method, java.lang.Object...)
     * @param interFaceClass
     * @param methodName
     * @param args
     * @return
     */
    @SneakyThrows
    public static DubboApiMetaData applyMeta(Class interFaceClass, String methodName , List<Object> args){
        String name = interFaceClass.getName();
        Class<?>[] classes = new Class<?>[args.size()];
        for (int i = 0; i < args.size(); i++) {
            classes[i] = args.get(i).getClass();
        }
        Method method = interFaceClass.getMethod(methodName, classes);
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = Arrays.stream(parameterTypes).
                map(Class::getName).
                toArray(String[]::new);
        Object[] objects = Optional.ofNullable(args).
                orElse(CollectionUtil.newArrayList()).toArray();

        DubboApiMetaData metaData = new DubboApiMetaData();
        metaData.setApiInterFacePath(name);
        metaData.setArgsType(paramTypes);
        metaData.setArgs(objects);
        metaData.setMethod(methodName);
        return metaData;
    }


    @SneakyThrows
    public static DubboApiMetaData applyMeta(Class<?> interFaceClass, String methodName ,Object[] args){
        String name = interFaceClass.getName();
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
        return metaData;
    }
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


    @Deprecated
    public static Protocol applyProtocol(Class clazz,String method,List object){
        DubboApiMetaData metaData = DubboGenericParse.applyMeta(clazz, method, object);
        Protocol protocol = new DubboProtocol();
        protocol.setProtocolBody(JSON.toJSONString(metaData));
        return protocol;
    }



    @SneakyThrows
    @SuppressWarnings(value = "all")
    public DubboApiMetaData applyMeta(Class interFaceClass, Method method){
        String name = interFaceClass.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = Arrays.stream(parameterTypes).map(Class::getName).toArray(String[]::new);
        DubboApiMetaData metaData = new DubboApiMetaData();
        metaData.setApiInterFacePath(name);
        metaData.setArgsType(paramTypes);
        metaData.setMethod(method.getName());

        return metaData;

    }

    public static void main(String[] args) {
        Integer[] integers = new Integer[]{12,34,4,5,5,5};
        String[] strings = Arrays.stream(integers).map(Object::toString).toArray(String[]::new);
        Arrays.stream(strings).forEach(System.out::print);

        System.out.println(AccountService.class.getPackage().getName());
        System.out.println(AccountService.class.getPackage().getName());
    }
}
