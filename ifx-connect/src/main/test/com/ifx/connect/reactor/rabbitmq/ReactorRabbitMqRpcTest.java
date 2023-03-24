package com.ifx.connect.reactor.rabbitmq;

import com.ifx.connect.proto.OnLineUser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
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
 * @date 2023/3/22
 */
@Slf4j
public class ReactorRabbitMqRpcTest {


    String queue = "rpc.server.queue";
    String queue2 = "amqp.rpc.reply.to";
    String exchange = "rpc.server.exchange";
    String routeKey = "rpc.server.routeKey";
    Sender sender ;
    Receiver receiver;
    String id = "233j3j1k31";

    @Test
    public void rpcClient() throws InterruptedException {
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
        RpcClient rpcClient = sender.rpcClient("", queue,()-> id);
        OnLineUser.UserSearch build = OnLineUser.UserSearch.newBuilder().build();
        Mono<Delivery> reply = rpcClient.rpc(Mono.just(
                new RpcClient.RpcRequest( "hello world".getBytes())
        )).doOnNext(e-> {
            byte[] body = e.getBody();
            String s = new String(body);
            log.info("接受的数据为 {}", s);
        });
        reply.subscribe();
        Thread.sleep(50000);
    }

    @Test
    public void rpcReceive () throws InterruptedException {
        Flux<Delivery> deliveryFlux = receiver.consumeAutoAck(queue2)
                .doOnNext(e -> {
                    String correlationId = e.getProperties().getCorrelationId();
                    byte[] body = e.getBody();
                    String s = new String(body);
                    log.info("传输的数据为 {}  {}", s,correlationId);
                    String replyTo = e.getProperties().getReplyTo();
                });
        deliveryFlux.subscribe();
        Thread.sleep(50000);
    }


    @Test
    public void rpcClientReceive() throws InterruptedException {

        Flux<Delivery> deliveryFlux = receiver.consumeAutoAck(queue)
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

    @BeforeEach
    public void init () {
        ConnectionFactory factory = ReactorRabbit.createFactory();
        SenderOptions senderOptions = new SenderOptions().connectionFactory(factory)
                .connectionSubscriptionScheduler(Schedulers.boundedElastic());
        ReceiverOptions receiverOptions = new ReceiverOptions()
                .connectionFactory(factory)
                .connectionSubscriptionScheduler(Schedulers.boundedElastic());
        sender = RabbitFlux.createSender(senderOptions);
        receiver = RabbitFlux.createReceiver(receiverOptions);

        declareExchange();
    }

    public void declareExchange(){
        sender.declare(ExchangeSpecification.exchange(exchange))
                .then(sender.declare(QueueSpecification.queue(queue)))
                .then(sender.declare(QueueSpecification.queue(queue2)))
                .then(sender.bind(BindingSpecification.queueBinding(exchange,routeKey,queue)))
                .subscribe(e-> log.info("The exchange and  queue is binding!"));
    }
}
