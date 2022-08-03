
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

实现基本群聊功能

其他待商议



### V2Plan