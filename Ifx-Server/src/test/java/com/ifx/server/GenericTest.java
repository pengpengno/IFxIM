//package com.ifx.server;
//
//import org.apache.dubbo.config.ApplicationConfig;
//import org.apache.dubbo.config.ReferenceConfig;
//import org.apache.dubbo.config.RegistryConfig;
//import org.apache.dubbo.rpc.RpcContext;
//import org.apache.dubbo.rpc.service.GenericService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CountDownLatch;
//
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//public class GenericTest {
//    @Value("${dubbo.registry.address}")
//    private String address;
//
//    @Test
//    public void generic(){
//        GenericService genericService;
//        //创建ApplicationConfig
//        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setName("accountService");
//        //创建注册中心配置
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress(address);
//        //创建服务引用配置
//
//        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
//        //设置接口
//        referenceConfig.setInterface("com.ifx.account.service.AccountService");
//        applicationConfig.setRegistry(registryConfig);
//        referenceConfig.setApplication(applicationConfig);
//        //重点：设置为泛化调用
//        //注：不再推荐使用参数为布尔值的setGeneric函数
//        //应该使用referenceConfig.setGeneric("true")代替
//        referenceConfig.setGeneric("true");
//
//        //设置异步，不必须，根据业务而定。
////        referenceConfig.setAsync(true);
//        //设置超时时间
//        referenceConfig.setTimeout(7000);
//
//        //获取服务，由于是泛化调用，所以获取的一定是GenericService类型
//        genericService = referenceConfig.get();
//
//        //使用GenericService类对象的$invoke方法可以代替原方法使用
//        //第一个参数是需要调用的方法名
//        //第二个参数是需要调用的方法的参数类型数组，为String数组，里面存入参数的全类名。
//        //第三个参数是需要调用的方法的参数数组，为Object数组，里面存入需要的参数。
//        String method = "login";
//        String[] type = new String[]{"com.ifx.account.vo.AccountBaseInfo"};
//        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
//        accountBaseInfo.setUserName("222");
//        Object[] datas = new Object[]{accountBaseInfo};
//
////        Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"world"});
//        Object result = genericService.$invoke(method,type,datas);
//        //使用CountDownLatch，如果使用同步调用则不需要这么做。
//        CountDownLatch latch = new CountDownLatch(1);
//        //获取结果
//        CompletableFuture<String> future = RpcContext.getContext().getCompletableFuture();
//        future.whenComplete((value, t) -> {
//            System.err.println("invokeSayHello(whenComplete): " + value);
//            latch.countDown();
//        });
//        //打印结果
//        System.out.println("invokeSayHello(return): " + result);
////        latch.await();
//    }
//}
