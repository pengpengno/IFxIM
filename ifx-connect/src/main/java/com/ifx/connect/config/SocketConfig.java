package com.ifx.connect.config;

import com.ifx.connect.connection.client.ClientAction;
import com.ifx.connect.connection.client.ClientLifeStyle;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.properties.ServerNettyConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(ServerNettyConfigProperties.class)
public class SocketConfig {

    @Resource
    private ServerNettyConfigProperties serverNettyConfigProperties;


    @Bean
    public ServerNettyConfigProperties apply(ServerNettyConfigProperties serverNettyConfigProperties){
        return serverNettyConfigProperties;
    }

    @Bean
    public ClientAction  clientAction(){
        return ClientToolkit.getDefaultClientAction();
    }



    @Bean
    public ClientLifeStyle clientLifeStyle(){
        return ClientToolkit.getDefaultClientLifeStyle();
    }


}
