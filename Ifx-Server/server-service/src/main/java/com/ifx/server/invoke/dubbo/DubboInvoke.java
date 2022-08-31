package com.ifx.server.invoke.dubbo;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.common.res.Result;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.server.invoke.GateInvoke;
import com.ifx.server.s2c.IServer2ClientAction;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
//public class DubboInvoke implements GateInvoke , ApplicationListener<ContextRefreshedEvent> {
public class DubboInvoke implements GateInvoke {
    @Value("${dubbo.registry.address}")
    private String address;
    @Value("${dubbo.application.name}")
    private String dubboServiceName;
    @Resource
    private IServer2ClientAction server2ClientAction;

    private RegistryConfig registryConfig;

    private ApplicationConfig applicationConfig;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("spring start / update succ ");
        //创建注册中心配置
        registryConfig = new RegistryConfig();
        registryConfig.setAddress(address);
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboServiceName);
        applicationConfig.setRegistry(registryConfig);
    }


    @Override
    public void doWork(ChannelHandlerContext channel, Protocol protocol) {
        try {
            //创建服务引用配置
            ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
            DubboApiMetaData metaData = JSONObject.parseObject(protocol.getBody(), DubboApiMetaData.class);
            //设置接口
            referenceConfig.setInterface(metaData.getApiInterFacePath());
            referenceConfig.setApplication(applicationConfig);
            referenceConfig.setGeneric("true");

            //设置异步，不必须，根据业务而定。 TODO 设计为动态
            referenceConfig.setAsync(true);
            //设置超时时间 TODO 设计动态
            referenceConfig.setTimeout(7000);
            //获取服务，由于是泛化调用，所以获取的一定是GenericService类型
            GenericService genericService = referenceConfig.get();
//            protocol.get
            Object result = genericService.$invoke(metaData.getMethod(), metaData.getArgsType(), metaData.getArgs());
            //使用CountDownLatch，如果使用同步调用则不需要这么做。
            CountDownLatch latch = new CountDownLatch(1);
            //获取结果
            CompletableFuture future = RpcContext.getServerContext().getCompletableFuture();
            future.whenComplete((value, t) -> {
                Result<Object> ok = Result.ok(value);
                protocol.setRes(ok);
                server2ClientAction.sendProtoCol(channel.channel(),protocol);
                log.info("doWork(whenComplete): " + value);
                latch.countDown();
            });
        } catch (Exception e) {
            log.error(ExceptionUtil.stacktraceToString(e));
        }
    }

    @Override
    public void doException(Throwable e) {

    }
}

