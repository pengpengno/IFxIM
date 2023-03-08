package com.ifx.client.config;

import com.ifx.connect.properties.ClientNettyConfigProperties;
import com.ifx.connect.properties.ServerRouteConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableConfigurationProperties({ServerRouteConfigProperties.class})
@Slf4j
public class ClientConnectConfig {

    @Autowired
    private ClientNettyConfigProperties clientNettyConfigProperties;

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

}
