package com.ifx.connect.connection.client.tcp;

import com.ifx.connect.handler.decoder.ProtocolDecoder;
import com.ifx.connect.handler.encoder.ProtocolEncoder;
import com.ifx.connect.handler.client.ClientNettyHandler;
import com.ifx.connect.proto.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;

@Slf4j
public class TcpNettyClient {

    private InetSocketAddress address;


    private Bootstrap bootstrap;

    private Channel channel;


    public Channel getChannel() {
        return channel;
    }



    public InetSocketAddress getAddress(){
        return address;
    }

    protected TcpNettyClient() {
    }
    

    public TcpNettyClient(String host, Integer port) throws Throwable {
        address = new InetSocketAddress(host,port);
        doOpen();
    }

    public TcpNettyClient(Integer port) throws Throwable {
        address = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),port);
        doOpen();
    }

    public void release(){
        channel.close();
    }

    /**
     * Init bootstrap 
     * 开始连接
     *
     * @throws Throwable
     */
    public ChannelFuture doOpen() throws Throwable {
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
                            socketChannel.pipeline()
                                    .addLast(new ProtocolEncoder())
                                    .addLast(new ProtocolDecoder())
                                    .addLast(new ClientNettyHandler())//添加Handler
                                    ;

                        }
                    });

            //连接到远程节点；等待连接完成
            ChannelFuture future = bootstrap.connect().sync();

            channel = future.channel();

            return future;

    }

    public ChannelFuture write(String msg){
        if (channel== null || !channel.isActive()){
            return null;
        }
        return channel.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    public ChannelFuture write(Protocol protocol){
        if (channel== null || !channel.isActive()){
            log.info( "channel  is null  or invaild");
            return null;
        }
        return channel.writeAndFlush(protocol);
    }




    private enum SingleInstance{
        INSTANCE;
        private final TcpNettyClient instance;
        SingleInstance(){
            instance = new TcpNettyClient();
        }
        private TcpNettyClient getInstance(){
            return instance;
        }
    }
    public static TcpNettyClient getInstance(){
        return TcpNettyClient.SingleInstance.INSTANCE.getInstance();
    }

}
