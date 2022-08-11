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
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class ServerProtoReceive implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${dubbo.registry.address}")
    private String address;

    private static GenericService genericService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        event.getApplicationContext().g
    }

    @DubboReference(version = "1.0.0")
    private AccountService accountService;
    public void received(ChannelHandlerContext ctx, Protocol protocol){
        try {

            //创建ApplicationConfig
            ApplicationConfig applicationConfig = new ApplicationConfig();
            applicationConfig.setName("generic-call-consumer");
            //创建注册中心配置
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress(address);
            //创建服务引用配置

            ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
            //设置接口
            referenceConfig.setInterface("org.apache.dubbo.samples.generic.call.api.HelloService");
            applicationConfig.setRegistry(registryConfig);
            referenceConfig.setApplication(applicationConfig);
//            referenceConfig.setScopeModel();
            //重点：设置为泛化调用
            //注：不再推荐使用参数为布尔值的setGeneric函数
            //应该使用referenceConfig.setGeneric("true")代替
            referenceConfig.setGeneric("true");

            //设置异步，不必须，根据业务而定。
            referenceConfig.setAsync(true);
            //设置超时时间
            referenceConfig.setTimeout(7000);

            //获取服务，由于是泛化调用，所以获取的一定是GenericService类型
            genericService = referenceConfig.get();

            //使用GenericService类对象的$invoke方法可以代替原方法使用
            //第一个参数是需要调用的方法名
            //第二个参数是需要调用的方法的参数类型数组，为String数组，里面存入参数的全类名。
            //第三个参数是需要调用的方法的参数数组，为Object数组，里面存入需要的参数。
            String method = "";
            String[] type ;
            Object[] datas;
            Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"world"});
            //使用CountDownLatch，如果使用同步调用则不需要这么做。
            CountDownLatch latch = new CountDownLatch(1);
            //获取结果
            CompletableFuture<String> future = RpcContext.getContext().getCompletableFuture();
            future.whenComplete((value, t) -> {
                System.err.println("invokeSayHello(whenComplete): " + value);
                latch.countDown();
            });
            //打印结果
            System.err.println("invokeSayHello(return): " + result);
            latch.await();


            String command = protocol.getCommand();
            CommandEnum commandEnum = CommandEnum.getByName(command);
            switch (commandEnum) {
                case LOGIN:
                    log.info("正在处理 login 请求额");
                    Boolean login = accountService.login(JSONObject.parseObject(protocol.getBody(), AccountBaseInfo.class));
                    Result<Boolean> res = Result.ok(CollectionUtil.newArrayList(login));
                    protocol.setRes(res);
                    Channel channel = ctx.channel();
                    channel.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(protocol), CharsetUtil.UTF_8));
                    log.info("服务端业务处理完毕  登录状态为{} ", login);
                default:
                    break;

            }
        }catch (Exception e){
            log.error(ExceptionUtil.stacktraceToString(e));
        }
    }
}
