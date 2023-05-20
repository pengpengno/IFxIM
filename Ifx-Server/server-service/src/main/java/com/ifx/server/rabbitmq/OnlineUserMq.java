package com.ifx.server.rabbitmq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.connect.connection.server.context.ConnectionContextUtil;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.proto.OnLineUser;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.rabbitmq.Receiver;

import java.io.IOException;

/**
 * 在线用户mq
 * @author pengpeng
 * @description
 * @date 2023/3/22
 */
@Service
@Slf4j
public class OnlineUserMq {

    @Autowired
    private Receiver receiver;

    @Value("${online.user.search.queue:online.user.search}")
    private String  onlineMqQueue;

    @Value("${online.user.receive.queue:online.user.receive}")
    private String serverOnlineInfoQueue;

    @Value("${online.user.exchange:online.user.exchange}")
    private String onlineUserExchange;

    @Value("${online.user.routeKey:online.user.search}")
    private String onlineUserRouteKey;

    @Autowired
    private IConnectContextAction contextAction;

    @Autowired
    private ConnectionContextUtil contextUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;



    @RabbitListener(
            bindings = {@QueueBinding(
                value = @Queue(value = "${online.user.search.queue:online.user.search}"),
                key = "${online.user.routeKey:online.user.search}",
                exchange = @Exchange(name = "${online.user.exchange:online.user.exchange}",type = ExchangeTypes.DIRECT))})
    public Message sendOnlineMessage(Message message, Channel channel){
        log.info("接受到了mq 的数据");
        try {
            byte[] body = message.getBody();
            OnLineUser.UserSearch userSearch = OnLineUser.UserSearch.parseFrom(body);
            log.info("正在搜索在线用户");
            Message res = new Message(contextUtil.filterOnlineByUserSearch(userSearch).toByteArray());
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return res;
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("传入数据格式非法！");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
