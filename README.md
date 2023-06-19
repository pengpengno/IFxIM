
## IFXIM

基于 ```netty ``` 通信的IM系统

### 主要技术栈

- 响应式编程[Reactor](https://projectreactor.io/docs)
- 事件驱动中间件[RabbitMq](https://www.rabbitmq.com/)
- 底层消息推送长连接服务器[Netty](https://netty.io/wiki/user-guide-for-4.x.html)
- 后台程序主体使用编程框架[Spring-Boot 3.x](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- API接口 [WebFlux](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web)
- 网络消息序列化[ProtocolBuf](https://protobuf.dev/getting-started/javatutorial/)


### Quick Start

First , make sure that  environment is meet the need 

docker
maven 
jdk

1. install jar locally
```
mvn clean install -DskipTests=true
```
2. Build docker Image
```shell

docker build ${SERVICE_NAME}:${VERSION} .
```
3. Push image to dockerhub

```shell
docker push  ${DOCKERHUB}/${IMAGE}
```
4. Start docker (Server side)
```shell
cd docker
docker-compose up -d
```


5. Start client

ifx-fxclient 中启动  ```APP``` 界面给定账号密码即可，详情可在./docker下的sql 查看

