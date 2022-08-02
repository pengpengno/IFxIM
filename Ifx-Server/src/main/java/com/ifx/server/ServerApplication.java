package com.ifx.server;

import com.ifx.connect.properties.SocketProperties;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ifx.server.netty.StartNettyServer;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.InetSocketAddress;

//@SpringBootApplication(scanBasePackages = {"com.ifx"})
@SpringBootApplication()
@Slf4j
public class ServerApplication implements CommandLineRunner {
//public class ServerApplication   {

    @Resource
    private StartNettyServer nettyServer;

    @Resource
    private SocketProperties socketProperties;


    public void run(String... args) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("netty 启动地址为 {} port {}",hostAddress,socketProperties.getPort());
        InetSocketAddress address = new InetSocketAddress(hostAddress, socketProperties.getPort());
        ChannelFuture channelFuture = nettyServer.bing(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
        channelFuture.channel().closeFuture().syncUninterruptibly();
//        // 开启服务
//        log.info("正在初始化Netty port {}",socketProperties.getPort());
//        ChannelFuture future = nettyServer.applyChannel( socketProperties.getPort());
//        log.info("Netty 启动完成 port {}",socketProperties.getPort());
//        // 在JVM销毁前关闭服务
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                log.info("Netty 正在关闭 ");
//                nettyServer.close();
//            }
//        });
//        future.channel().closeFuture().sync();
    }
    public static void main(String[] args) {
            SpringApplication.run(ServerApplication.class);
    }
}
