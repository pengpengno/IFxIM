
## IFX
A IM system which is build in JavaFx

during my college  time , i design my first im sys ,  and i git it to github , but that time  i am poor at git skill, so that 
my project was upload one by one manual. cause lots of file was lost; 

i decide to rebuild this project   in five months

## project dev plan 
v1  will work on socket TCP to get a basic Application
and will design the flowing module
1. account  
3. message
4. meeting room
5. connection
## V1 plan
实现IM系统基本功能的实现
### account 

账户基本功能 

登录 注册  忘记密码 注销

实现头像功能

断线重连

更多自定义基本信息 邮箱，工作组等等 （待商议）

### connect

connect V1版本实现基于TCP 的自定义应用层通讯协议

设计基于netty的通信框架

this is ifx core connection
### message

实现消息分级（权重）

实现单聊场景下写扩散

实现群聊场景下的读写混合

实现离线写

实现上线读

### relation

实现好友系统

1添加 、删除好友

2.上线好友列表自动拉取

### meeting room

使用多人群聊下的 读写混合（超大群 使用读扩散， 量级百人以下使用写扩散）



### V2Plan
对后台功能进行封装设计
### 通信模块
封装netty 模块，避免```IFX```通信模块对netty的强依赖  （可以调整到V3）
#### 解析层、 传输层
使用```Protocol Buffers``` 实现消息的序列化传输
#### 转发层
### 客户端
#### 视图层
1. 重构 视图展示底层逻辑，将沿用Spring设计思想 将视图控制权 交给容器
实现 javafx-spring-starter
2. 使用css  以及外部组件美化样式 ，对UI 进行美化
### 解析层
实现与客户端通信时的数据传输，兼容dubbo协议进行，避免通过 使用枚举 ```command ```指令进行业务模块解析



