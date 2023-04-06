package com.ifx.connect.config;

import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.ReactiveServerAction;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.ConnectionContextUtil;
import com.ifx.connect.connection.server.context.IConnectContextAction;
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
@ConditionalOnProperty(prefix = "ifx.connect.server",name = "port")
public class ServerBootstrap {




    @Bean
    @ConditionalOnMissingBean(IConnectContextAction.class)
    public IConnectContextAction applyIConnectContextAction(){
        return ServerToolkit.contextAction();
    }

    @Bean
    @ConditionalOnBean(IConnectContextAction.class)
    @ConditionalOnMissingBean(ConnectionContextUtil.class)
    public ConnectionContextUtil applyConnectionContextUtil(){
        return ConnectionContextUtil.getInstance();
    }


    @Bean
    @ConditionalOnBean(value = {ServerNettyConfigProperties.class})
    public ReactiveServer initServer(@Autowired ServerNettyConfigProperties serverNettyConfigProperties){
        ReactiveServer reactiveServer = ServerToolkit.reactiveServer();
        Integer port = serverNettyConfigProperties.getPort();
        String host = serverNettyConfigProperties.getHost();
        InetSocketAddress address = new InetSocketAddress(host,port);
         reactiveServer.init(address);
         return reactiveServer;
    }

    @Bean
    @ConditionalOnBean(value = {ReactiveServer.class})
    public ReactiveServerAction reactiveServerAction(){
        return ServerToolkit.reactorTcpServerAction();
    }
}
