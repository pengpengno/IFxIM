package com.ifx.server.rabbitmq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.connect.proto.Chat;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${message.chat.queue:message.chat.queue}")
    private String messageQueue;


    @Value("${message.chat.exchange:message.chat.exchange}")
    private String exchange;

    @Value("${message.action.send:message.action.send}")
    private String routingKey;



    @RabbitListener(
            bindings = {@QueueBinding(
                    value = @Queue(value = "${message.chat.send.queue:message.chat.send.queue}"),
                    key = "${message.action.send:message.action.send}",
                    exchange = @Exchange(name = "${message.chat.exchange:message.chat.exchange}",type = ExchangeTypes.DIRECT))})
    public void  handlerMessageSend(Message message, Channel channel){

        byte[] chatMessageByte = message.getBody();

        if (chatMessageByte != null){
            try{
                Chat.ChatMessage chatMessage = Chat.ChatMessage.parseFrom(chatMessageByte);

            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        }



    }
}
