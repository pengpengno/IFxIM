package com.ifx.connect.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/23
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"host","port","username","password","virtual-host"})
public class SpringAMQPConfig {


//    @Bean
//    @ConditionalOnMissingBean(ConnectionFactory.class)
//    ConnectionFactory connectionFactory(@Autowired RabbitProperties rabbitProperties) {
//        ConnectionFactory connectionFactory = new CachingConnectionFactory().getRabbitConnectionFactory();
//        connectionFactory.setHost(rabbitProperties.getHost());
//        connectionFactory.setPort(rabbitProperties.getPort());
//        connectionFactory.setUsername(rabbitProperties.getUsername());
//        connectionFactory.setPassword(rabbitProperties.getPassword());
//        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
////        connectionFactory.useNio();
//        return connectionFactory;
//    }

//    @Bean
//    @ConditionalOnMissingBean(org.springframework.amqp.rabbit.connection.ConnectionFactory.class)
//    @ConditionalOnBean(ConnectionFactory.class)
//    org.springframework.amqp.rabbit.connection.ConnectionFactory springConnectionFactory (@Autowired ConnectionFactory connectionFactory) {
//        return new CachingConnectionFactory(connectionFactory);
//    }

//    @Bean
//    @ConditionalOnBean(org.springframework.amqp.rabbit.connection.ConnectionFactory.class)
//    public RabbitTemplate springRabbitTemplate(@Autowired org.springframework.amqp.rabbit.connection.ConnectionFactory conn){
//        return new RabbitTemplate(conn);
//    }





}
