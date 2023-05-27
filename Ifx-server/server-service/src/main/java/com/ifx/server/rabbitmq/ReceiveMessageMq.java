package com.ifx.server.rabbitmq;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.connect.connection.server.ReactiveServerAction;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;
import com.ifx.exec.ex.net.NetException;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

import java.io.IOException;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/22
 */
@Slf4j
@Component
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

    @Autowired
    ReactiveServerAction reactiveServerAction;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(
            bindings = {@QueueBinding(
            value = @Queue(value = "${message.chat.send.queue:message.chat.send.queue}"),
            key = "${message.action.send:message.action.send}",
            exchange = @Exchange(name = "${message.chat.exchange:message.chat.exchange}",type = ExchangeTypes.DIRECT))})
    @RabbitHandler
    public void  handlerMessageSend(Message message, Channel channel) throws IOException {

        byte[] chatMessageByte = message.getBody();
        log.debug("Rabbit has receive the chat message ");
        if (chatMessageByte != null){
            try{

                Chat.ChatMessage chatMessage = Chat.ChatMessage.parseFrom(chatMessageByte);

                sendChatMessage(chatMessage);

            } catch (InvalidProtocolBufferException e) {
                log.error("Occur an error  when try to send chat message .  Program will send feedback to downStream business that currently message sent failed !  {}" , ExceptionUtil.stacktraceToString(e));
                throw new RuntimeException(e);
            }finally {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }
        }

    }

    /***
     * sent chat message
     * @param chatMessage
     */
    public void sendChatMessage(Chat.ChatMessage chatMessage){
        if (null == chatMessage){
            log.warn("Warning  ,  some empty data occur in chatMessage sending , it will not be processed!");
            return;
        }
        Account.AccountInfo toAccountInfo = chatMessage.getToAccountInfo();
        String account = toAccountInfo.getAccount();

        if (StrUtil.isNotBlank(account)){
            try{
                reactiveServerAction.sendMessage(account,chatMessage).subscribe();
            }catch (NetException exception){
                deliveryFailBack(chatMessage);
                log.error("The account {}  occur an error that  maybe  connection is drop off or offLine",account);
            }
        }
    }

    /**
     * Notice the downStream business that specify chat message is in a failed processed status
     * @param chatMessage
     */
    public  void  deliveryFailBack(Chat.ChatMessage chatMessage){
        log.warn("send the feedback to mq ");


    }
}
