package com.ifx.server.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/16
 */
public class RabbitMq {

    @Bean
    @ConditionalOnMissingBean(ConnectionFactory.class)
    @ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"host","port","username","password"})
    ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.useNio();
        return connectionFactory;
    }



}
