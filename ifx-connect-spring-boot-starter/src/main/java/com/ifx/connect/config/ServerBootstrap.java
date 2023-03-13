package com.ifx.connect.config;

import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.tcp.ReactorTcpServer;
import com.ifx.connect.properties.ServerNettyConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * 长连接 bean
 * @author pengpeng
 * @description
 * @date 2023/3/10
 */

@Configuration
@EnableConfigurationProperties(ServerNettyConfigProperties.class)
@ConditionalOnClass(value = {ReactiveServer.class, ReactorTcpServer.class})
@ConditionalOnProperty(prefix = "ifx.connect.netty.server",name = "port")
public class ServerBootstrap {


    @Bean
    @ConditionalOnMissingBean(value = {ReactiveServer.class, ReactorTcpServer.class})
    public ReactiveServer applyReactiveServer(){
        return ServerToolkit.reactiveServer();
    }




    @Bean
    @ConditionalOnBean(value = {ReactiveServer.class, ReactorTcpServer.class ,ServerNettyConfigProperties.class})
    public ReactiveServer startServer(@Autowired ServerNettyConfigProperties properties,@Autowired ReactiveServer reactiveServer){
        Integer port = properties.getPort();
        InetSocketAddress address = new InetSocketAddress(port);
        reactiveServer.start(address);
        return reactiveServer;
    }
}
