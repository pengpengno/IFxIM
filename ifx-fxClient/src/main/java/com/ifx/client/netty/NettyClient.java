package com.ifx.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.Data;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;


public class NettyClient {

    private InetSocketAddress address;

    private Bootstrap bootstrap;

    private static NettyClient instance ;

    private volatile Channel channel;



    private NettyClient(){
    }

    public NettyClient(String host,Integer port) throws Throwable {
        address = new InetSocketAddress(host,port);
        doOpen();
    }

    public NettyClient(Integer port) throws Throwable {
        address = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),port);
        doOpen();
    }

    public static NettyClient getInstance() {
        if (instance == null) {
            instance = new NettyClient();
        }
        return instance;
    }

    /**
     * Init bootstrap
     *
     * @throws Throwable
     */
    protected ChannelFuture doOpen() throws Throwable {
        /**
         * @Description  配置相应的参数，提供连接到远端的方法
         **/
        EventLoopGroup group = new NioEventLoopGroup();   //I/O线程池
//        try {
        bootstrap = new Bootstrap();//客户端辅助启动类
        bootstrap.group(group)
                    .channel(NioSocketChannel.class)//实例化一个Channel
                    .remoteAddress(address)
                    .handler(new ChannelInitializer<SocketChannel>()//进行通道初始化配置
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            socketChannel.pipeline().addLast(new ClientHandler());//添加我们自定义的Handler
                        }
                    });

            //连接到远程节点；等待连接完成
            ChannelFuture future=bootstrap.connect().sync();
//            //发送消息到服务器端，编码格式是utf-8
//            future.channel().writeAndFlush(Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));

            channel = future.channel();
//        channel.writeAndFlush(Unpooled.copiedBuffer("倒萨倒萨倒萨", CharsetUtil.UTF_8));
            //阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
//            future.channel().closeFuture().sync();
            instance = this;
            return future;


//        } finally {
//            group.shutdownGracefully().sync();
//        }
    }

    public void write(String msg){
        channel.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        channel.write(msg);
    }


}
