package com.ifx.connect.reactor.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;
import reactor.test.StepVerifier;

import java.io.IOException;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/22
 */
@Slf4j
public class ReactorRabbitMqRpcTest {


    String queue = "rpc.server.queue";
    String exchange = "rpc.server.exchange";
    String routeKey = "rpc.server.routeKey";
    Sender sender ;
    Receiver receiver;
    String id = "233j3j1k31";

    @Test
    public void rpcClient(){
        RpcClient rpcClient = sender.rpcClient("", queue,()-> id);
        Mono<Delivery> reply = rpcClient.rpc(Mono.just(
                new RpcClient.RpcRequest("hello".getBytes())
        )).doOnNext(e-> {
            byte[] body = e.getBody();
            String s = new String(body);
            log.info("接受的数据为 {}", s);
        });
        StepVerifier.create(reply)
                .verifyComplete();
    }


    public class HookServer extends RpcServer{

        public HookServer(Channel channel) throws IOException {
            super(channel);
        }

    }
    @Test
    public void rpcClientReceive() throws InterruptedException {


        Flux<Delivery> deliveryFlux = receiver.consumeAutoAck(queue)
                .doOnNext(e -> {
                    String correlationId = e.getProperties().getCorrelationId();
                    byte[] body = e.getBody();
                    String s = new String(body);
                    log.info("传输的数据为 {}  {}", s,correlationId);
                    String replyTo = e.getProperties().getReplyTo();
//                    e.getProperties().writePropertiesTo();
//                    sender.rpcClient("",queue,()-> correlationId).rpc(Mono.just(new RpcClient.RpcRequest("I have receive ".getBytes()))).subscribe();
//                    e.getEnvelope().getExchange()
                });
        deliveryFlux.subscribe();
        Thread.sleep(50000);
//        StepVerifier.create(deliveryFlux)
//                .verifyComplete();

    }

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
        sender.declare(ExchangeSpecification.exchange(exchange))
                .then(sender.declare(QueueSpecification.queue(queue)))
                .then(sender.bind(BindingSpecification.queueBinding(exchange,routeKey,queue)))
                .subscribe(e-> log.info("The exchange and  queue is binding!"));
    }
}
