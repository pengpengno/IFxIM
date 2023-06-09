package com.ifx.client.config;

import com.ifx.client.app.pane.message.ChatMainPane;
import com.ifx.connect.properties.ServerRouteConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
@Configuration
@EnableConfigurationProperties({ServerRouteConfigProperties.class})
@Slf4j
public class ClientApiBootstrap {

    @Bean("server")
    @ConditionalOnBean(ServerRouteConfigProperties.class)
    @ConditionalOnProperty(prefix = "ifx.api.gateway",name = {"host","port"})
    public HttpClient httpClient(@Autowired ServerRouteConfigProperties properties){
        return HttpClient.create()
                .host(properties.getHost())
                .port(properties.getPort());
    }


    /**
     * 默认 webclient
     * @param client
     * @return 返回 webFlux http调用实例
     */
    @Bean("webClient")
    @ConditionalOnBean(HttpClient.class)
    public WebClient webclient(@Autowired HttpClient client){
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }


    @Bean
    public ChatMainPane chatMainPane(){
        return ChatMainPane.getInstance();
    }
}
