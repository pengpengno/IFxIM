package com.ifx.server;

import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.properties.ServerNettyConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.InetSocketAddress;

@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
@EnableConfigurationProperties({ServerNettyConfigProperties.class})
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private ServerNettyConfigProperties config;

    @Autowired
    private ReactiveServer server;


    public void run(String... args)  {
//        server.start();
       server
        .start(new InetSocketAddress(config.getHost(),config.getPort()));
        Runtime.getRuntime().addShutdownHook(new Thread(() ->server.stop()));
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}
