package com.ifx.connect.connection.server.tcp;

import com.ifx.connect.handler.decoder.ProtocolDecoder;
import com.ifx.connect.handler.encoder.ProtocolEncoder;
import com.ifx.connect.handler.server.ServerServiceParseHandler;
import com.ifx.connect.proto.dubbo.DubboApiService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Slf4j
public class TcpNettyServer {

    private static final int MAX_FRAME_DATA_SIZE = 102400;

    private static  final int LENGTH_OFFSET = 0;

    private static  final int PROTOCOL_BODY_LENGTH = 8;

    private static  final int LENGTH_ADJUSTMENT  = 8;

    private static  final int HEADER_LENGTH_SIZE  = 8;

    private enum SingleInstance{
        INSTANCE;
        private final TcpNettyServer instance;
        SingleInstance(){
            instance = new TcpNettyServer();
        }
        private TcpNettyServer getInstance(){
            return instance;
        }
    }
    public static TcpNettyServer getInstance(){
        return SingleInstance.INSTANCE.getInstance();
    }

    //配置服务端NIO线程组
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();
    //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    private final EventLoopGroup childGroup = new NioEventLoopGroup();
    private Channel channel;
    public ChannelFuture bind(InetSocketAddress address) {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new IdleStateHandler(20,20,20)) //  free channel  checkout
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ch.pipeline()
                                    .addLast(new ProtocolEncoder())
                                    .addLast(new ProtocolDecoder())
                                    .addLast(new LoggingHandler())
                                    .addLast(new ServerServiceParseHandler());
                        }
                    });
            channelFuture = b.bind(address).syncUninterruptibly();
             channel = channelFuture.channel();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                log.info("netty server start done. host {}  port {}",address.getHostName(),address.getPort());
            } else {
                log.error("netty server start error. ");
            }
        }
        return channelFuture;
    }

    public void createServer(InetSocketAddress address){
        TcpServer tcpServer = TcpServer.create();
        DisposableServer server =
                tcpServer
                        .host(address.getAddress().getHostAddress())
                        .port(address.getPort())
                        .doOnConnection(connection -> connection.addHandlerFirst(new IdleStateHandler(20,20,20)))//  free channel  checkout
                        .doOnChannelInit((connectionObserver, channel, remoteAddress) -> {
                            channel.pipeline()
                                    .addLast(new ProtocolEncoder())
                                    .addLast(new ProtocolDecoder())
//                                    .addLast(new LoggingHandler())
                                    .addLast(new ServerServiceParseHandler());
                        })
                        .bindNow()
                ;
        server.onDispose()
                .block();
        log.info("netty server start done. host {}  port {}",address.getHostName(),address.getPort());
    }


    public void destroy() {
        if (null == channel) return;
        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }



}
