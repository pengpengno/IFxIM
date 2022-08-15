package com.ifx.client.connect.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Component
public class NettyClient {

    private InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),8976);  //

    private Bootstrap bootstrap;


    private Channel channel;


    public Channel getChannel() {
        return channel;
    }


    public InetSocketAddress getAddress(){
        return address;
    }
    private NettyClient() throws UnknownHostException {
    }

    public NettyClient(String host,Integer port) throws Throwable {
        address = new InetSocketAddress(host,port);
        doOpen();
    }

    public NettyClient(Integer port) throws Throwable {
        address = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),port);
        doOpen();
    }

    public void doOpen(Integer port) throws InterruptedException, UnknownHostException {
        /**
         * @Description  配置相应的参数，提供连接到远端的方法
         **/
        EventLoopGroup group = new NioEventLoopGroup();   //I/O线程池
//        try {
        bootstrap = new Bootstrap();//客户端辅助启动类
        bootstrap.group(group)
                .channel(NioSocketChannel.class)//实例化一个Channel
                .remoteAddress(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),port))
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

        channel = future.channel();

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
//        DefaultEventExecutorGroup eventExecutors = new DefaultEventExecutorGroup();

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

            channel = future.channel();

            return future;

    }

    public ChannelFuture write(String msg){
        if (channel== null || !channel.isActive()){
            return null;
        }
        return channel.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }


}
