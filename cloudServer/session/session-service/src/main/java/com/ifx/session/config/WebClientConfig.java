package com.ifx.session.config;

import com.ifx.connect.properties.ServerRouteConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/2
 */
@Configuration
public class WebClientConfig {



    @Bean("server")
    @ConditionalOnBean(ServerRouteConfigProperties.class)
    public HttpClient httpClient(@Autowired ServerRouteConfigProperties properties){
        return HttpClient.create()
                .host(properties.getHost())
                .port(properties.getPort());
    }


    /**
     * 默认 webclient
     * @param client
     * @return
     */
    @Bean("webClient")
    @ConditionalOnBean(HttpClient.class)
    public WebClient webclient(@Autowired HttpClient client){
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }
}
