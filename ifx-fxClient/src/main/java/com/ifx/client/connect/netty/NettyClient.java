package com.ifx.client.connect.netty;

import com.ifx.connect.decoder.ProtocolDecoder;
import com.ifx.connect.encoder.ProtocolEncoder;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import com.ifx.connect.proto.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Component
@Slf4j
@EnableConfigurationProperties({ClientNettyConfigProperties.class})
public class NettyClient implements ApplicationListener<ContextRefreshedEvent>
{

//    private InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),8976);  //
//    @Resource
    private InetSocketAddress address;

    @Resource
    private ClientNettyConfigProperties clientNettyConfigProperties;

//    @Resource
//    private ProtocolDecoder protocolDecoder;
//
//    @Resource
//    private ProtocolEncoder protocolEncoder;

    private Bootstrap bootstrap;

    private Channel channel;


    public Channel getChannel() {
        return channel;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String serverHost = clientNettyConfigProperties.getServerHost();
        log.info("客户端【netty】配置为serverHost {}port{}",
                serverHost,clientNettyConfigProperties.getServerPort());
        address =  new InetSocketAddress(Optional.ofNullable(serverHost).orElse(InetAddress.getLocalHost().getHostAddress() ),
                clientNettyConfigProperties.getServerPort());
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
                .remoteAddress(new InetSocketAddress(clientNettyConfigProperties.getServerHost(),
                        clientNettyConfigProperties.getServerPort()))
                .handler(new ChannelInitializer<SocketChannel>()//进行通道初始化配置
                {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception
                    {
                        socketChannel.pipeline()
                                .addLast(new ProtocolEncoder())
                                .addLast(new ProtocolDecoder())
                                .addLast(new ClientNettyHandler())
                                ;//添加自定义的Handler
                    }

                });

        //连接到远程节点；等待连接完成
        ChannelFuture future = bootstrap.connect().sync();

        channel = future.channel();

    }

    protected void release(){
        channel.close();
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
                            socketChannel.pipeline()
                                    .addLast(new ProtocolEncoder())
                                    .addLast(new ProtocolDecoder())
                                    .addLast(new ClientNettyHandler())//添加我们自定义的Handler
                                    ;//添加自定义的Handler

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

    public ChannelFuture write(Protocol protocol){
        if (channel== null || !channel.isActive()){
            return null;
        }
        return channel.writeAndFlush(protocol);
    }


}
