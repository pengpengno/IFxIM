package com.ifx.connect.connection.client.tcp;

import com.ifx.connect.handler.client.ClientBusinessHandler;
import com.ifx.connect.handler.decoder.ProtocolDecoder;
import com.ifx.connect.handler.encoder.ProtocolEncoder;
import com.ifx.connect.proto.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.net.InetSocketAddress;

/***
 * Tcp netty 实现
 */
@Slf4j
public class TcpNettyClient {

    private InetSocketAddress address;


    private Bootstrap bootstrap;

    private Channel channel;

    private Connection connection;
    public Channel getChannel() {
        return channel;
    }



    public InetSocketAddress getAddress(){
        return address;
    }

    protected TcpNettyClient() {
    }
    

    public TcpNettyClient(String host, Integer port) throws InterruptedException {
        address = new InetSocketAddress(host,port);
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


    public ChannelFuture doOpen() throws InterruptedException {
        return doOpen(address);
    }
    public ChannelFuture doOpen(InetSocketAddress address) throws InterruptedException  {
        /**
         * @Description  配置相应的参数，提供连接到远端的方法
         **/
        EventLoopGroup group = new NioEventLoopGroup();   //I/O线程池
        bootstrap = new Bootstrap();//客户端辅助启动类
        bootstrap.group(group)
                    .channel(NioSocketChannel.class)//实例化一个Channel
                    .remoteAddress(address)
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                    socketChannel.pipeline()
                    .addLast(new ProtocolEncoder())
                    .addLast(new ProtocolDecoder())
                    .addLast(new ClientBusinessHandler())//添加Handler
                                    ;
                        }
                    });
            //连接到远程节点；等待连接完成
            ChannelFuture future = bootstrap.connect().sync();
            channel = future.channel();
            return future;

    }

    /***
     * reactor-netty的实现
     * @param address
     * @return
     */
    public Boolean create(InetSocketAddress address){
         connection =
                TcpClient.create()
                        .host(address.getHostName())
                        .port(address.getPort())
                        .doOnConnect((tcpClientConfig)-> log.info("连接建立！"))
//                        .bindAddress(() -> address)
                        .doOnChannelInit((connectionObserver, channel1, remoteAddress) -> {
                            channel1.pipeline()
                                    .addLast(new ProtocolEncoder())
                                    .addLast(new ProtocolDecoder())
                                    .addLast(new ClientBusinessHandler())//添加Handler
                                    ;
                            })
                        .connectNow();
//        connection.onDispose()
//                .block()
        ;
        log.info("客户端连接完毕！");
        return Boolean.TRUE;
    }



    public ChannelFuture write(Protocol protocol){
        if (channel== null || !channel.isActive()){
            log.info( "channel  is null  or invaild");
            return null;
        }
        return channel.writeAndFlush(protocol);
    }

    public void outBound (Protocol protocol){
        connection.outbound().sendObject(protocol);
    }

    public Boolean isAlive(){
//        connection !=null;
        return connection.isDisposed();
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
