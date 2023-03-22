package com.ifx.server.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/22
 */
public class ReceiveMessageMq {

    @Autowired
    public Sender sender;

    @Autowired
    public Receiver receiver;
}
