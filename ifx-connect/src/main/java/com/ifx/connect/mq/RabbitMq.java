package com.ifx.connect.mq;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.ConnectionFactory;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.SenderOptions;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
public class RabbitMq {

    public void initMq(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        SenderOptions senderOptions =  new SenderOptions()
                .connectionFactory(connectionFactory)
                .connectionSupplier(cf -> cf.newConnection(
                        new Address[] {new Address("localhost"), new Address("192.168.0.2")},
                        "reactive-sender"))
                .resourceManagementScheduler(Schedulers.boundedElastic());

    }
}
