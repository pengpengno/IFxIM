## netty
### netty 使用
1. 实现自定义ServerHandler  用作处理```Netty``` ``` Channel``` 的操作 
2. 实现自定义ClientHandler  
3. 实现服务端启动类 ServerStartUp
4. 实现客户端启动类 ClientStartUp

> 自定义服务端Handler


```

@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//        TODO  implents netty serial
        //处理收到的数据，并反馈消息到到客户端
        ByteBuf in = (ByteBuf) msg;
        System.out.println("收到客户端发过来的消息: " + in.toString(CharsetUtil.UTF_8));
        log.info("收到客户端发过来的消息: {}" , in.toString(CharsetUtil.UTF_8));
        //写入并发送信息到远端（客户端）
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是服务端，我已经收到你发送的消息", CharsetUtil.UTF_8));

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        //出现异常的时候执行的动作（打印并关闭通道）
        cause.printStackTrace();
        ctx.close();
    }

```


>  自定义客户端Handler


```Java
@Slf4j
@Component
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        /**
         * @Description  处理接收到的消息
         **/
        log.info("receive msg from server-side {}, data package {}",ctx.channel().localAddress().toString(),byteBuf);
        System.out.println("接收到的消息："+byteBuf.toString(CharsetUtil.UTF_8));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        /**
         * @Description  处理I/O事件的异常
         **/
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("【netty】服务已连接");
    }
}
```

> 服务端启动类

```Java
    public ChannelFuture applyChannel(Integer port) throws InterruptedException {
//            EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
//            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap(); // (2)
                b.group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class) // (3)
                        .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new ServerHandler());
//                                ch.pipeline().addLast(serverHandler);
                            }
                        })
                        .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                        .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

                // Bind and start to accept incoming connections.
                return b.bind("127.0.0.1",port).sync(); // (7)
                // Wait until the server socket is closed.
                // In this example, this does not happen, but you can do that to gracefully
                // shut down your server.
//                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw e;
            } finally {
                workGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }

```

> 客户端启动类


```Java

public class ClientNettyConfig {

    private final String host;
    private final int port;

    public ClientNettyConfig(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception
    {
        /**
         * @Description  配置相应的参数，提供连接到远端的方法
         **/
        EventLoopGroup group = new NioEventLoopGroup();   //I/O线程池
        try {
            Bootstrap bs = new Bootstrap();//客户端辅助启动类
            bs.group(group)
                    .channel(NioSocketChannel.class)//实例化一个Channel
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>()//进行通道初始化配置
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            socketChannel.pipeline().addLast(new ClientHandler());//添加我们自定义的Handler
                        }
                    });

            //连接到远程节点；等待连接完成
            ChannelFuture future=bs.connect().sync();

            //发送消息到服务器端，编码格式是utf-8
            future.channel().writeAndFlush(Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));

            //阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
            future.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception
    {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        new ClientNettyConfig(hostAddress,8976).run();
    }
}
```