package com.ifx.connect.config;

import com.ifx.connect.connection.client.ClientLifeStyle;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.connection.client.ReactiveClientAction;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
@Configuration
@ConditionalOnProperty(prefix = "ifx.connect.client",name = {"serverPort","serverHost"})
public class ClientConnectBootstrap {


    @ConditionalOnMissingBean(value = {ReactiveClientAction.class})
    @Bean
    public ReactiveClientAction applyClientAction(){
        return ClientToolkit.reactiveClientAction();
    }


    @Bean
    public ClientLifeStyle connectServer(@Autowired ClientNettyConfigProperties properties){
        ClientLifeStyle lifeStyle = ClientToolkit.clientLifeStyle();
        String serverHost = properties.getServerHost();
        Integer serverPort = properties.getServerPort();
        InetSocketAddress address = new InetSocketAddress(serverHost, serverPort);
        lifeStyle.init(address);
        return lifeStyle;
    }


}
