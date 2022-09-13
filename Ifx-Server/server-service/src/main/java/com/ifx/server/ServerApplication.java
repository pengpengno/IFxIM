package com.ifx.server;

import com.ifx.connect.properties.ServerNettyConfigProperties;
import com.ifx.server.netty.StartNettyServer;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.InetSocketAddress;

@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
@DubboComponentScan
@EnableConfigurationProperties({ServerNettyConfigProperties.class})
@EnableDubbo
public class ServerApplication implements CommandLineRunner {

    @Resource
    private StartNettyServer nettyServer;

    @Resource
    private ServerNettyConfigProperties serverNettyConfigProperties;

    public void run(String... args)  {
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String hostAddress =serverNettyConfigProperties.getHost();
        log.info("netty 启动地址为 {} port {}",hostAddress, serverNettyConfigProperties.getPort());
        InetSocketAddress address = new InetSocketAddress(hostAddress, serverNettyConfigProperties.getPort());
        ChannelFuture channelFuture = nettyServer.bind(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
    }

    public static void main(String[] args) {
            SpringApplication.run(ServerApplication.class);
    }
}
