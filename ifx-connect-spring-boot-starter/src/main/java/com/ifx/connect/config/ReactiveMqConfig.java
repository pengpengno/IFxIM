package com.ifx.connect.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/22
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"host","port","username","password"})
@AutoConfigureAfter(SpringAMQPConfig.class)
public class ReactiveMqConfig {


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
    @ConditionalOnBean(ConnectionFactory.class)
    public Sender applySender(@Autowired ConnectionFactory connectionFactory){
        SenderOptions senderOptions = new SenderOptions()
                .connectionFactory(connectionFactory)
                .resourceManagementScheduler(Schedulers.boundedElastic());
        return RabbitFlux.createSender(senderOptions);
    }


    @Bean
    @ConditionalOnBean(ConnectionFactory.class)
    public Receiver applyReceiver(@Autowired ConnectionFactory connectionFactory){
        ReceiverOptions receiverOptions = new ReceiverOptions()
                .connectionFactory(connectionFactory)
                .connectionSubscriptionScheduler(Schedulers.boundedElastic());
        return RabbitFlux.createReceiver(receiverOptions);
    }


}
