package com.ifx.connect.reactor.rabbitmq;

import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.OnLineUser;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/24
 */
@Slf4j
public class ReactorOnlineUserTest {
    Sender sender ;
    Receiver receiver;
    private final String  onlineMqQueue ="online.user.search";

    @BeforeEach
    public void init () {
        SenderOptions senderOptions = new SenderOptions().connectionFactory(ReactorRabbit.createFactory())
                .connectionSubscriptionScheduler(Schedulers.boundedElastic());
        ReceiverOptions receiverOptions = new ReceiverOptions()
                .connectionFactory(ReactorRabbit.createFactory())
                .connectionSubscriptionScheduler(Schedulers.boundedElastic());
        sender = RabbitFlux.createSender(senderOptions);
        receiver = RabbitFlux.createReceiver(receiverOptions);
        declareExchange();
    }

    public void declareExchange(){
        sender.declareQueue(QueueSpecification.queue(onlineMqQueue).durable(true))
                .subscribe(e-> log.info("The exchange and  queue is binding!"));
    }

    @Test
    public void onlineUserTest() throws InterruptedException {
        RpcClient rpcClient = sender.rpcClient("", onlineMqQueue);
        OnLineUser.UserSearch pengpeng = OnLineUser.UserSearch.newBuilder()
                .addAccounts(Account.AccountInfo.newBuilder().setAccount("pengpeng").build())
                .build();
        Mono<Delivery> reply = rpcClient.rpc(Mono.just(
                new RpcClient.RpcRequest( pengpeng.toByteArray())
        )).doOnNext(e-> {
            byte[] body = e.getBody();
            String s = new String(body);
            log.info("接受的数据为 {}", s);
        });

        reply.subscribe();
        Thread.sleep(50000);
    }


    @Test
    public void receive() throws InterruptedException {
        Flux<Delivery> deliveryFlux = receiver.consumeAutoAck(onlineMqQueue)
                .doOnNext(e -> {
                    String correlationId = e.getProperties().getCorrelationId();
                    byte[] body = e.getBody();
                    String s = new String(body);
                    String replyTo = e.getProperties().getReplyTo();
                    log.info("传输的数据为 {}  {}  {} " , s,correlationId,replyTo);
                    sender.rpcClient("",replyTo,()-> correlationId).rpc(Mono.just(new RpcClient.RpcRequest("I have receive  message".getBytes()))).subscribe();
                });
        deliveryFlux.subscribe();
        Thread.sleep(50000);
    }
}
