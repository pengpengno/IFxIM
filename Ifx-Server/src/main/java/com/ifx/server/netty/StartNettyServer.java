package com.ifx.server.netty;

import com.ifx.connect.netty.server.Server;
import com.ifx.connect.netty.server.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Component("nettyServer")
@Slf4j
//public class StartNettyServer   implements Server, ApplicationListener<ContextRefreshedEvent> {
//public class StartNettyServer   implements Server {
public class StartNettyServer {

    //配置服务端NIO线程组
    private final EventLoopGroup parentGroup = new NioEventLoopGroup(); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    private final EventLoopGroup childGroup = new NioEventLoopGroup();
    private Channel channel;
    public ChannelFuture bing(InetSocketAddress address) {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ServerHandler());
            channelFuture = b.bind(address).syncUninterruptibly();
            channel = channelFuture.channel();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                log.info("itstack-demo-netty server start done. ");
            } else {
                log.error("itstack-demo-netty server start error. ");
            }
        }
        return channelFuture;
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
//
////    @Autowired
////    private SocketProperties socketProperties;
//    @Value("${ifx.connect.socket.port}")
//    private Integer port;
//
//    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
//    private final EventLoopGroup workGroup = new NioEventLoopGroup();
////    @Resource
////    private ServerHandler serverHandler;
//
//    public void startUp(Integer port) throws InterruptedException {
//            ChannelFuture channelFuture = applyChannel(port);
//            // Wait until the server socket is closed.
//                    // In this example, this does not happen, but you can do that to gracefully
//                    // shut down your server.
////            channelFuture.channel().closeFuture().sync();
//        }
//
//    public ChannelFuture applyChannel(Integer port) throws InterruptedException {
////            EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
////            EventLoopGroup workerGroup = new NioEventLoopGroup();
//            try {
//                ServerBootstrap b = new ServerBootstrap(); // (2)
//                b.group(bossGroup, workGroup)
//                        .channel(NioServerSocketChannel.class) // (3)
//                        .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
//                            @Override
//                            public void initChannel(SocketChannel ch) throws Exception {
//                                ch.pipeline().addLast(new ServerHandler());
////                                ch.pipeline().addLast(serverHandler);
//                            }
//                        })
//                        .option(ChannelOption.SO_BACKLOG, 128)          // (5)
//                        .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
//
//                // Bind and start to accept incoming connections.
//                return b.bind("127.0.0.1",port).sync(); // (7)
//                // Wait until the server socket is closed.
//                // In this example, this does not happen, but you can do that to gracefully
//                // shut down your server.
////                f.channel().closeFuture().sync();
//            } catch (InterruptedException e) {
//                throw e;
//            } finally {
//                workGroup.shutdownGracefully();
//                bossGroup.shutdownGracefully();
//            }
//        }
//
////    @Override
////    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
////        new StartNettyServer().startNetty(port);
////
////    }
//    public void close() {
//        workGroup.shutdownGracefully();
//        bossGroup.shutdownGracefully();
//        log.info("======Shutdown Netty Server Success!=========");
//    }
//    public void start(Integer port) throws InterruptedException {
//            EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
//            EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap(); // (2)
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class) // (3)
//                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
//                        @Override
//                        public void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new ServerHandler());
//                        }
//                    })
//                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
//
//            // Bind and start to accept incoming connections.
//            ChannelFuture f = b.bind(port).sync(); // (7)
//
//            // Wait until the server socket is closed.
//            // In this example, this does not happen, but you can do that to gracefully
//            // shut down your server.
//                f.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//            throw e;
//        } finally {
//            workGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//        }
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        new StartNettyServer().start(1111);
//    }


}
