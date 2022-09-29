package com.ifx.connect.proto.dubbo;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;

@Slf4j
public class DubboApiService {


    private DubboApiService(){}

    private enum SingleInstance{
        INSTANCE;
        private final DubboApiService instance;
        SingleInstance(){
            instance = new DubboApiService();
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
        DubboApiMetaData metaData = JSONObject.parseObject(protocol.getProtocolBody(), DubboApiMetaData.class);
        return applyMethod(metaData);
    }

    public static Method applyMethod(DubboApiMetaData metaData) throws ClassNotFoundException{
        try{
            String apiInterFacePath = metaData.getApiInterFacePath();
            String[] argsType = metaData.getArgsType();
            int argsLength = argsType.length;
            Class<?> apiClass = ClassLoaderUtil.loadClass(apiInterFacePath);
            Class<?>[] classes = new Class<?>[argsLength];
//            load class
            for (int i = 0; i < argsLength; i++) {
                classes[i] = ClassLoaderUtil.loadClass(argsType[i]);
            }
            String method = metaData.getMethod();
            Method declaredMethod = ClassUtil.getDeclaredMethod(apiClass, method, classes);
            return declaredMethod;

        } catch (UtilException utilException){
            log.info("接口不存在,协议体为{} ", JSON.toJSONString(metaData));
            throw new ClassNotFoundException("no class found in  \n"+ JSON.toJSONString(metaData));
        }
    }
    public static Class<?> applyApiClass(Protocol protocol) throws ClassNotFoundException{
        try{

            DubboApiMetaData metaData = JSONObject.parseObject(protocol.getProtocolBody(), DubboApiMetaData.class);
            String apiInterFacePath = metaData.getApiInterFacePath();
            String[] argsType = metaData.getArgsType();
            int argsLength = argsType.length;
            Class<?> apiClass = ClassLoaderUtil.loadClass(apiInterFacePath);
            return apiClass;
        } catch (UtilException utilException){
            log.info("接口不存在,协议体为{} ", JSON.toJSONString(protocol));
            throw new ClassNotFoundException("no class found in  \n"+ JSON.toJSONString(protocol));
        }
    }



    public  Method applyMethod(String protocolBody){
        return null;
    }
}
