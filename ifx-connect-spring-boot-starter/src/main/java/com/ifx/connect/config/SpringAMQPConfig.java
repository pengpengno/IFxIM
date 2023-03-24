package com.ifx.connect.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/23
 */
@Configuration
@ConditionalOnClass(value = {Sender.class, Receiver.class,RabbitProperties.class})
@ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"host","port","username","password","virtual-host"})
public class SpringAMQPConfig {


    @Bean
    @ConditionalOnMissingBean(ConnectionFactory.class)
    @ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"host","port","username","password","virtual-host"})
    ConnectionFactory connectionFactory(@Autowired RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = new CachingConnectionFactory().getRabbitConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
//        connectionFactory.useNio();
        return connectionFactory;
    }

    @Bean
    @ConditionalOnMissingBean(org.springframework.amqp.rabbit.connection.ConnectionFactory.class)
    @ConditionalOnBean(ConnectionFactory.class)
    org.springframework.amqp.rabbit.connection.ConnectionFactory springConnectionFactory (@Autowired ConnectionFactory connectionFactory) {
        return new CachingConnectionFactory(connectionFactory);
    }





}
