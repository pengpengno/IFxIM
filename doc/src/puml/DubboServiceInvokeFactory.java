package com.humancloud.saas.factory;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * Company: hotpay
 *
 * @author: zixuan.yang
 * @since: 2021/11/22 12:11
 */
@Component
public class DubboServiceInvokeFactory {


    Logger logger = LoggerFactory.getLogger(DubboServiceInvokeFactory.class);

    @Value("${dubbo.application.name}")
    private String name;

    @Value("${dubbo.registry.address}")
    private String address;

    @Value("${dubbo.protocol.name}")
    private String protocolName;

    @Value("${dubbo.protocol.port}")
    private Integer protocolPort;

    @Value("${dubbo.consumer.timeout}")
    private Integer timeout;


    public <T> Object genericInvoke(String interfaceClass, String methodName, String version, LinkedHashMap<String, Object> parametersMap) throws Exception {
        logger.info("interfaceClass{},methodName{},parametersMap{}", interfaceClass, methodName, parametersMap);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(address);
        registryConfig.setPort(protocolPort);
        registryConfig.setProtocol(protocolName);
        // 引用远程服务
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setRegistry(registryConfig);
        reference.setRetries(0);
        reference.setInterface(interfaceClass); // 接口名
        reference.setGeneric("true"); // 声明为泛化接口
        reference.setCheck(false);
        reference.setVersion(version);
        reference.setTimeout(timeout);
        reference.setAsync(true);
        GenericService genericService = ReferenceConfigCache.getCache().get(reference);
        if (CollectionUtil.isEmpty(parametersMap)) {
            genericService.$invoke(methodName, null, null);
            CompletableFuture<Object> fooFuture = RpcContext.getContext().getCompletableFuture();
            return fooFuture.get(30, TimeUnit.SECONDS);
        }
        int parametersSize = parametersMap.size();
        String[] invokeParamTypes = new String[parametersSize];
//            Method[] methods = genericService.getClass().getMethods();
//            AtomicBoolean atomicBoolean = new AtomicBoolean(Boolean.FALSE);
//            Arrays.stream(methods).forEach(item -> {
//                if (Objects.equals(item.getName(), methodName)) {
//                    Class<?>[] classes = item.getParameterTypes();
//                    int length = classes.length;
//                    if (parametersSize == length) {
//                        for (int i = 0; i < length; i++) {
//                            invokeParamTypes[i] = classes[i].getTypeName();
//                        }
//                        atomicBoolean.set(Boolean.TRUE);
//                        return;
//                    }
//                }
//            });
//            if (!atomicBoolean.get()) {
//                logger.info("genericInvoke interfaceClass:{},methodName:{},version:{},parameters:{}", interfaceClass, methodName, version, JSONObject.toJSONString(parametersMap));
////                throw new CustomException(ErrorEnum.PARAMETER_ABNORMAL);
//                return null;
//            }
        Object[] invokeValues = new Object[parametersSize];
        AtomicInteger integer = new AtomicInteger(0);
        parametersMap.entrySet().forEach(item -> {
            int i = integer.getAndIncrement();
            invokeValues[i] = item.getValue();
            Object value = item.getValue();
            if (value instanceof HashMap) {
                invokeParamTypes[i] = Map.class.getTypeName();
            } else if (value instanceof ArrayList) {
                invokeParamTypes[i] = List.class.getTypeName();
            } else {
                invokeParamTypes[i] = value.getClass().getTypeName();
            }

        });
        genericService.$invoke(methodName, invokeParamTypes, invokeValues);
        CompletableFuture<T> fooFuture = RpcContext.getContext().getCompletableFuture();
        return fooFuture.get(30, TimeUnit.SECONDS);


    }

    public static void main(String[] args) {
        try {
            ApplicationConfig application = new ApplicationConfig();
            application.setName("com.humancloud.saas.configcenter.service");
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://118.31.47.95:2181");
            application.setRegistry(registryConfig);
            // 引用远程服务
            ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
            // reference.setApplication(application);
            reference.setRegistry(registryConfig);
            reference.setRetries(0);
            reference.setInterface("com.hunmancloud.saas.api.ICalcEngin2ManagerService"); // 接口名
            reference.setGeneric(true); // 声明为泛化接口
            reference.setCheck(false);
            reference.setVersion("2.0.0");
            reference.setTimeout(200);
            reference.setAsync(true);
            GenericService genericService = reference.get();
            Object object = genericService.$invoke("getCalcList", new String[]{"Long"}, new Object[]{1438709282228224L});
        } catch (Exception e) {
            System.out.println("err");
        }
        ;
    }
}
