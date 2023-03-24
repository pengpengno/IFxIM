package com.ifx.server.rabbitmq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.connect.connection.server.context.ConnectionContextUtil;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.proto.OnLineUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

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
    private Sender sender;

    @Autowired
    private ConnectionContextUtil contextUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;



//    /***
//     * 接受在线状态搜索
//     */
//    public void receiveOnlineSearch(){
//
//        Flux<AcknowledgableDelivery> onLineUser = receiver.consumeManualAck(onlineMqQueue);
//        onLineUser.map(delivery -> {
//            byte[] body = delivery.getBody();
//            try {
//                return OnLineUser.UserSearch.parseFrom(body);
//            } catch (InvalidProtocolBufferException e) {
//                delivery.nack(false);
//                throw new IllegalArgumentException(e);
//            }
//        }).onErrorResume(throwable -> Mono.empty())
//        .map(userSearch -> contextUtil.filterOnlineByUserSearch(userSearch))
//        .map(userSearch -> sender.send(Mono.just(new OutboundMessage(onlineUserExchange,onlineUserRouteKey,userSearch.toByteArray())))).subscribe();
//    }

    @RabbitListener(
            bindings = {@QueueBinding(
                value = @Queue(value = "${online.user.search.queue:online.user.search}"),
                key = "${online.user.routeKey:online.user.search}",
                exchange = @Exchange(name = "${online.user.exchange:online.user.exchange}",type = ExchangeTypes.DIRECT))})
    public OnLineUser.UserSearch sendOnlineMessage(byte[] body){
        log.info("接受到了mq 的数据");
        try {
            OnLineUser.UserSearch userSearch = OnLineUser.UserSearch.parseFrom(body);
            log.debug("正在搜索在线用户");
            return contextUtil.filterOnlineByUserSearch(userSearch);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
