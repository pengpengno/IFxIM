
## IFXIM

基于 ```netty ``` 通信的IM系统

### 主要技术栈
 
- 响应式编程[Reactor](https://projectreactor.io/docs)
- 事件驱动中间件[RabbitMq](https://www.rabbitmq.com/)
- 底层消息推送长连接服务器[Netty](https://netty.io/wiki/user-guide-for-4.x.html)
- 后台程序主体使用编程框架API接口[Spring-Boot 3.x](https://spring.io/projects/spring-boot)
- 网络消息序列化[ProtocolBuf](https://protobuf.dev/getting-started/javatutorial/)

### Feature
✨ 消息分级（权重）
✨ 单聊场景下写扩散
✨ 群聊场景下的读扩散

### Plan
目前正在重新梳理通讯层协议 以 ```Reactive Programming ```设计 通讯层
- 通讯序列化 Protobuf
- Reactive 架构 WebFlux
- 通讯协议 尚未决定 RSocket or thrift or RPC ?
- GUI JavaFx
