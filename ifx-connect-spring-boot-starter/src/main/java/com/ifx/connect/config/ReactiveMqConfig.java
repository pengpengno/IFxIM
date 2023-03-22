package com.ifx.connect.config;

import com.ifx.connect.properties.ServerNettyConfigProperties;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(ServerNettyConfigProperties.class)
@ConditionalOnClass(value = {Sender.class, Receiver.class})
@ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"host","port","username","password"})
public class ReactiveMqConfig {


    @Bean
    @ConditionalOnMissingBean(ConnectionFactory.class)
    @ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"host","port","username","password","virtualHost"})
    ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
        connectionFactory.useNio();
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
