package com.ifx.server;

import com.ifx.connect.properties.ServerNettyConfigProperties;
import com.ifx.server.netty.TcpNettyServer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import reactor.core.publisher.Mono;
import java.net.InetSocketAddress;

@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
@EnableConfigurationProperties({ServerNettyConfigProperties.class})
public class ServerApplication implements CommandLineRunner {

    @Resource
    private ServerNettyConfigProperties serverNettyConfigProperties;

    public void run(String... args)  {
        Mono.fromCallable(()->
            new InetSocketAddress(serverNettyConfigProperties.getHost(),
                    serverNettyConfigProperties.getPort()))
            .subscribe(inetSocketAddress -> {
                TcpNettyServer.getInstance().bind(inetSocketAddress);
                Runtime.getRuntime().addShutdownHook(new Thread(() -> TcpNettyServer.getInstance().destroy()));
            });

    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}
