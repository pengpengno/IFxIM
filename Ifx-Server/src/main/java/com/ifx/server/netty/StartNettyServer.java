package com.ifx.server.netty;

import com.ifx.connect.netty.server.Server;
import com.ifx.connect.netty.server.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//public class StartNettyServer   implements Server, ApplicationListener<ContextRefreshedEvent> {
public class StartNettyServer   implements Server {

//    @Autowired
//    private SocketProperties socketProperties;
    @Value("${ifx.connect.socket.port}")
    private Integer port;

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
//    @Resource
//    private ServerHandler serverHandler;

    public void startUp(Integer port) throws InterruptedException {
            ChannelFuture channelFuture = applyChannel(port);
            // Wait until the com.ifx.server socket is closed.
                    // In this example, this does not happen, but you can do that to gracefully
                    // shut down your com.ifx.server.
//            channelFuture.channel().closeFuture().sync();
        }

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
                // Wait until the com.ifx.server socket is closed.
                // In this example, this does not happen, but you can do that to gracefully
                // shut down your com.ifx.server.
//                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw e;
            } finally {
                workGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }

//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        new StartNettyServer().startNetty(port);
//
//    }
    public void close() {
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("======Shutdown Netty Server Success!=========");
    }
    public void start(Integer port) throws InterruptedException {
            EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
            EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the com.ifx.server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your com.ifx.server.
                f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new StartNettyServer().start(1111);
    }


}
