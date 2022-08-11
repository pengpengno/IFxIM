package com.ifx.server;

import com.ifx.connect.properties.ServerNettyConfigProperties;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ifx.server.netty.StartNettyServer;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.InetSocketAddress;

@SpringBootApplication(scanBasePackages = {"com.ifx"})
@ComponentScan(basePackages = {"com.ifx"})
@Slf4j
@EnableDubbo
public class ServerApplication implements CommandLineRunner {

    @Resource
    private StartNettyServer nettyServer;

    @Resource
    private ServerNettyConfigProperties serverNettyConfigProperties;


    public void run(String... args) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("netty 启动地址为 {} port {}",hostAddress, serverNettyConfigProperties.getPort());
        InetSocketAddress address = new InetSocketAddress(hostAddress, serverNettyConfigProperties.getPort());
        ChannelFuture channelFuture = nettyServer.bind(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
    }
    public static void main(String[] args) {
            SpringApplication.run(ServerApplication.class);
    }
}
