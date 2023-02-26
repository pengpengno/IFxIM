package com.ifx.connect.config;

import com.ifx.connect.properties.ServerNettyConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServerNettyConfigProperties.class)
public class SocketConfig {

//    @Resource
//    private ServerNettyConfigProperties serverNettyConfigProperties;
//
//
//    @Bean
//    public ServerNettyConfigProperties apply(ServerNettyConfigProperties serverNettyConfigProperties){
//        return serverNettyConfigProperties;
//    }
//
//    @Bean
//    public ClientAction  clientAction(){
//        return ClientToolkit.getDefaultClientAction();
//    }
//
//
//
//    @Bean
//    public ClientLifeStyle clientLifeStyle(){
//        return ClientToolkit.getDefaultClientLifeStyle();
//    }


}
