package com.ifx.server.rabbitmq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.connect.connection.server.context.ConnectionContextUtil;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.proto.OnLineUser;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.AcknowledgableDelivery;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

/**
 * 在线用户mq
 * @author pengpeng
 * @description
 * @date 2023/3/22
 */
@Service
public class OnlineUserMq {

    @Autowired
    private Receiver receiver;

    @Value("${online.user.search.queue:online.user.search}")
    private String  onlineMqQueue;

    @Value("${online.user.receive.queue:online.user.receive}")
    private String serverOnlineInfoQueue;

    @Value("${online.user.exchange:online.user.exchange}")
    private String onlineUserExchange;

    @Value("${online.user.routeKey}")
    private String onlineUserRouteKey;

    @Autowired
    private IConnectContextAction contextAction;

    @Autowired
    private Sender sender;

    @Autowired
    private ConnectionContextUtil contextUtil;



    /***
     * 接受在线状态搜索
     */
    public void receiveOnlineSearch(){

        Flux<AcknowledgableDelivery> onLineUser = receiver.consumeManualAck(onlineMqQueue);
        onLineUser.map(delivery -> {
            byte[] body = delivery.getBody();
            try {
                OnLineUser.UserSearch userSearch = OnLineUser.UserSearch.parseFrom(body);
                return userSearch;
            } catch (InvalidProtocolBufferException e) {
                delivery.nack(false);
                throw new IllegalArgumentException(e);
            }
        }).onErrorResume(throwable -> Mono.empty())
        .map(userSearch -> contextUtil.filterOnlineByUserSearch(userSearch))
        .map(userSearch -> sender.send(Mono.just(new OutboundMessage(onlineUserExchange,onlineUserRouteKey,userSearch.toByteArray())))).subscribe();
    }

    @RabbitListener(queues = {"${online.user.search.queue:online.user.search}"},
            bindings = {@QueueBinding(
                value = @Queue(value = "${online.user.search.queue:online.user.search}"),
                key = "${online.user.routeKey}",
                exchange = @Exchange(name = "${online.user.exchange:online.user.exchange}",type = ExchangeTypes.DIRECT))})
    public void sendOnlineMessage(byte[] data){


    }
}
