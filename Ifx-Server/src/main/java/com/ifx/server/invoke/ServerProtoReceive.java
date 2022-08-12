package com.ifx.server.invoke;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.common.res.Result;
import com.ifx.connect.enums.CommandEnum;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class ServerProtoReceive implements GateInvoke,ApplicationListener<ContextRefreshedEvent> {
    @Value("${dubbo.registry.address}")
    private String address;
    @Value("${dubbo.application.name}")
    private String dubboServiceName;

    private RegistryConfig registryConfig;
    private ApplicationConfig applicationConfig;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("spring start / update succ ");
       applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboServiceName);
        applicationConfig.setRegistry(registryConfig);
        //创建注册中心配置
        registryConfig = new RegistryConfig();
        registryConfig.setAddress(address);
    }

    @Override
    public void doWork(ChannelHandlerContext channel, Protocol protocol) {
        received(channel,protocol);
    }

    public void received(ChannelHandlerContext ctx, Protocol protocol){
        try {
            //创建服务引用配置

            ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
            DubboApiMetaData metaData = JSONObject.parseObject(protocol.getProtocol(), DubboApiMetaData.class);

            //设置接口
            referenceConfig.setInterface(metaData.getMethod());
            referenceConfig.setApplication(applicationConfig);
            referenceConfig.setGeneric("true");

            //设置异步，不必须，根据业务而定。 TODO 设计为动态
            referenceConfig.setAsync(true);
            //设置超时时间 TODO 设计动态
            referenceConfig.setTimeout(7000);
            //获取服务，由于是泛化调用，所以获取的一定是GenericService类型
            GenericService genericService = referenceConfig.get();
//            protocol.get
            Object result = genericService.$invoke(metaData.getMethod(),metaData.getArgsType(), new Object[]{"world"});
            //使用CountDownLatch，如果使用同步调用则不需要这么做。
//            CountDownLatch latch = new CountDownLatch(1);
            //获取结果
            CompletableFuture<String> future = RpcContext.getContext().getCompletableFuture();
            future.whenComplete((value, t) -> {
                System.err.println("invokeSayHello(whenComplete): " + value);
//                latch.countDown();
            });
            //打印结果
            System.err.println("invokeSayHello(return): " + result);
//            latch.await();

        }catch (Exception e){
            log.error(ExceptionUtil.stacktraceToString(e));
        }
    }
}
