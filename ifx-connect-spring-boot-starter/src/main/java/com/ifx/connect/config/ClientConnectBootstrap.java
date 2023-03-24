package com.ifx.connect.config;

import com.ifx.connect.connection.client.ClientLifeStyle;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.connection.client.ReactiveClientAction;
import com.ifx.connect.connection.client.tcp.reactive.ReactorTcpClient;
import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.tcp.ReactorTcpServer;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import com.ifx.connect.properties.ServerNettyConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
@Configuration
@EnableConfigurationProperties(ServerNettyConfigProperties.class)
@ConditionalOnClass(value = {ReactiveServer.class, ReactorTcpServer.class})
public class ClientConnectBootstrap {


    @ConditionalOnMissingBean(value = {ReactiveClientAction.class, ReactorTcpClient.class})
    public ReactiveClientAction applyClientAction(){
        return ClientToolkit.reactiveClientAction();
    }


    @ConditionalOnMissingBean(value = {ClientLifeStyle.class, ReactorTcpClient.class})
    public ClientLifeStyle applyLifeStyle(){
        return ClientToolkit.clientLifeStyle();
    }

    @ConditionalOnBean(value = {ReactiveClientAction.class, ReactorTcpClient.class})
    @ConditionalOnProperty(prefix = "ifx.connect.client",name = {"serverPort","serverHost"})
    public ClientLifeStyle connectServer(@Autowired ClientNettyConfigProperties properties, @Autowired ClientLifeStyle lifeStyle){
        String serverHost = properties.getServerHost();
        Integer serverPort = properties.getServerPort();
        InetSocketAddress address = new InetSocketAddress(serverHost, serverPort);
        Boolean connect = lifeStyle.connect(address);
        return lifeStyle;
    }


}
