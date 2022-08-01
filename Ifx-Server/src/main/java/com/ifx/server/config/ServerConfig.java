package com.ifx.server.config;

import com.ifx.connect.properties.SocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;

@Configuration
@EnableConfigurationProperties(SocketProperties.class)
@Slf4j
//public class ServerConfig implements ApplicationListener<ContextStartedEvent> {
public class ServerConfig{
    @Value("${ifx.connect.socket.port}")
    private Integer  port;


//    @Bean("netty")
//    public Server applyNetty(SocketProperties socketProperties){
//        StartNettyServer startNettyServer = new StartNettyServer();
//        startNettyServer.startNetty(socketProperties.getPort());
////        log.info("netty start succ on  port {} ",socketProperties.getPort());
//        return startNettyServer;
//    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//    }

    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        log.info("初始化 netty ---------------");
//        StartNettyServer startNettyServer = new StartNettyServer();
//        startNettyServer.startNetty(port);
        log.info("初始化 netty 完毕---------------");
    }
}
