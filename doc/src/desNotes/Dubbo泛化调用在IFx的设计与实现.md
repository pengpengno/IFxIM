## Dubbo泛化调用
Dubbo 泛化调用可以实现消费者和生产者的高度解耦 ，对于消费端无需引入服务方的api 即可完成调用
使用于实现网关、测试平台等；
### 如何实现泛化调用
1. 创建ApplicationConfig
2. 创建注册中心配置
3. 注入服务相关配置
4. 设置泛化调用
直接上代码
```
private static String zookeeperAddress = "zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181";
  //创建ApplicationConfig
 public static void main(String[] args) throws Exception {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("generic-impl-provider");
        //创建注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zookeeperAddress);
        
        //新建服务实现类，注意要使用GenericService接收
        GenericService helloService = new GenericImplOfHelloService();

        //创建服务相关配置
        ServiceConfig<GenericService> service = new ServiceConfig<>();
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        service.setInterface("org.apache.dubbo.samples.generic.call.api.HelloService");
        service.setRef(helloService);
        //重点：设置为泛化调用
        //注：不再推荐使用参数为布尔值的setGeneric函数
        //应该使用referenceConfig.setGeneric("true")代替
        service.setGeneric("true");
        service.export();

        System.out.println("dubbo service started");
        
        new CountDownLatch(1).await();
        }
```