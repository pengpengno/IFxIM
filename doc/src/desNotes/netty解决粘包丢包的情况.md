## 底层通信中出现的拆包与粘包

### 什么是拆包和粘包
采用RPC的微服务通信框架下，多数实现各个服务之间都会维护自身的TCP长连接来保持通信
所有的通讯请求都会通过这个全双工的通道发送，在数据频繁交互的场景下，如果没有采用合适方案就极易出现粘包，拆包的现象

![img.png](img.png)
TCP是‘字节流’协议 应用层和TCP传输层交换的是不同大小的数据模块，但是TCP将这些数据块视为无结构的字节流，其帧结构也没有标识数据长度，
所以可以视作传输的数据无边界，也因此会导致粘包和拆包的情况
### 拆包场景复现
在查询用户数据接口中，由于数据大小超出了netty 中预设的ByteBuf的1024 , 数据被拆包，导致客户端第一次接收到的数据非正常数据


### netty 中的解决方案
#### 方案原理

#### 方案实现

### netty 传输源码分析
> LengthFieldBasedFrameDecoder 解码器

```
    /**
     * Creates a new instance.
     *
     * @param maxFrameLength      最大帧长度。也就是可以接收的数据的最大长度。如果超过，此次数据会被丢弃。
     * @param lengthFieldOffset   长度域偏移。就是说数据开始的几个字节可能不是表示数据长度，需要后移几个字节才是长度域。
     * @param lengthFieldLength   长度域字节数。用几个字节来表示数据长度。
     * @param lengthAdjustment    数据长度修正。因为长度域指定的长度可以是header+body的整个长度，也可以只是body的长度。如果表示header+body的整个长度，那么我们需要修正数据长度。
     * @param initialBytesToStrip 跳过的字节数。如果你需要接收header+body的所有数据，此值就是0，如果你只想接收body数据，那么需要跳过header所占用的字节数。
     * @param failFast            如果为true，则在解码器注意到帧的长度将超过maxFrameLength时立即抛出TooLongFrameException，而不管是否已读取整个帧。
     *                            如果为false，则在读取了超过maxFrameLength的整个帧之后引发TooLongFrameException。
     */
    public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                                        int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        //略
    }
```
#### 底层拆包原理


