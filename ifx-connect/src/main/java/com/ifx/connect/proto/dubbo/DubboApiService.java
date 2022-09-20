package com.ifx.connect.proto.dubbo;

import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.func.Supplier1;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class DubboApiService {


    private DubboApiService(){}
//    private static ConcurrentHashMap<String,Class<?>> classConcurrentHashMap;

    private enum SingleInstance{
        INSTANCE;
        private final DubboApiService instance;
        SingleInstance(){
            instance = new DubboApiService();
//            classConcurrentHashMap = new ConcurrentHashMap<>();
        }
        private DubboApiService getInstance(){
            return instance;
        }
    }
    public static DubboApiService getInstance(){
        return SingleInstance.INSTANCE.getInstance();
    }
//    private volatile static  DubboApiService instance = null;
//    public static synchronized DubboApiService getInstance(){
//        if (instance == null ){
//            synchronized (DubboApiService.class) {
//                // 抢到锁之后再次判断是否为空
//                if (instance == null) {
//                    instance = new DubboApiService();
//                }
//            }
//        }
//        return instance;
//    }

    public static Method applyMethod(Protocol protocol) throws ClassNotFoundException{
        try{
            DubboApiMetaData metaData = JSONObject.parseObject(protocol.getProtocolBody(), DubboApiMetaData.class);
            String apiInterFacePath = metaData.getApiInterFacePath();
            String[] argsType = metaData.getArgsType();
//            CheckedUtil
            List<Class<?>> collect = Arrays.stream(argsType).
                    map(e -> CheckedUtil.uncheck((Func0<Class<?>>) () -> ClassLoaderUtil.loadClass(e),
                            e1 -> new RuntimeException()).call())
                    .collect(Collectors.toList());
            Object[] objects = collect.toArray();
            String method = metaData.getMethod();
            Class<?> apiClass = CheckedUtil.uncheck((Func0<Class<?>>) () -> ClassLoaderUtil.loadClass(apiInterFacePath),
                    e1 -> new RuntimeException()).call();
//            TODO  反射拉取 数据类型
            ClassUtil.getDeclaredMethod(apiClass,method,objects)

            return apiClass.getMethod()


        }
        catch (ClassNotFoundException classNotFoundException){
            log.info("接口不存在,协议体为{} ", JSON.toJSONString(protocol));
        }
        catch (Exception ex){

        }

    }


    public  Method applyMethod(String protocolBody){
        return null;
    }
}
