package com.ifx.server.config;

import com.ifx.connect.properties.ServerNettyConfigProperties;
import com.ifx.server.netty.StartNettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(ServerNettyConfigProperties.class)
@Slf4j
public class ServerConfig{


    @Resource
    private StartNettyServer nettyServer;

    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        log.info("初始化 netty ---------------");
//        StartNettyServer startNettyServer = new StartNettyServer();
//        startNettyServer.startNetty(port);
        log.info("初始化 netty 完毕---------------");
    }
}
