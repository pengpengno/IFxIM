package com.ifx.server;

import com.google.inject.Guice;
import com.ifx.connect.connection.server.tcp.ReactorTcpServer;
import com.ifx.connect.properties.ServerNettyConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
//@EnableConfigurationProperties({ServerNettyConfigProperties.class})
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private ServerNettyConfigProperties config;

    public void run(String... args)  {
        ReactorTcpServer instance = Guice.createInjector().getInstance(ReactorTcpServer.class);
        instance
        .create(new InetSocketAddress(config.getHost(),config.getPort()));
//                        Runtime.getRuntime().addShutdownHook(new Thread(() -> TcpNettyServer.getInstance().destroy()));
//        Mono.fromCallable(()->
//            new InetSocketAddress(serverNettyConfigProperties.getHost(),
//                    Optional.ofNullable(serverNettyConfigProperties.getPort()).orElse(8094)))
//            .subscribe(inetSocketAddress -> {
//                TcpNettyServer.getInstance().bind(inetSocketAddress);
//                Runtime.getRuntime().addShutdownHook(new Thread(() -> TcpNettyServer.getInstance().destroy()));
//            });

    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}
