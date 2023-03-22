package com.ifx.connect.reactor.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;
import reactor.test.StepVerifier;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/17
 */
@Slf4j
public class ReactorRabbit {

    private static String host  = "localhost";
    private static Integer port  = 5672;

    private static String username = "admin";
    private static  String password  = "wangpeng";
    private static  String virtualHost  = "reactormq";

    @Test
    public void send() throws InterruptedException {
        SenderOptions senderOptions = new SenderOptions()
                .connectionFactory(createFactory())
                .resourceManagementScheduler(Schedulers.boundedElastic());
        Sender sender = RabbitFlux.createSender(senderOptions);
        Flux<OutboundMessage> map = Flux.range(0, 10)
                .map(i -> new OutboundMessage("reactive.rabbit", "chat.message", ("sent tot you " + i).getBytes()));
        Mono<AMQP.Exchange.DeclareOk> exchange = sender.declareExchange(ExchangeSpecification.exchange("reactive.ifx"));
        exchange.subscribe();
        Mono<AMQP.Queue.DeclareOk> queue = sender.declareQueue(QueueSpecification.queue("receive.message"));
        queue.subscribe();
        Mono<AMQP.Queue.BindOk> bind = sender.bind(BindingSpecification.binding().queueBinding("reactive.rabbit", "chat.message", "receive.message"));
        bind.subscribe();
//        sender.send(map).subscribe();
//        Thread.sleep(60000);
        StepVerifier.create(sender.send(map)
                .doOnError(e-> log.info(e.getMessage())))
                .verifyComplete();
    }



    @Test
    public void receiver () throws InterruptedException {
        ReceiverOptions receiverOptions = new ReceiverOptions()
                .connectionFactory(createFactory())
                .connectionSubscriptionScheduler(Schedulers.boundedElastic());
        Receiver receiver = RabbitFlux.createReceiver(receiverOptions);
        Flux<AcknowledgableDelivery> deliveryFlux = receiver.consumeManualAck("receive.message");
        Flux<AcknowledgableDelivery> acknowledgableDeliveryFlux = deliveryFlux.doOnNext(ack -> {
            String s = new String(ack.getBody());
            log.info("the receive message is {}", s);
            ack.ack();
        });
        acknowledgableDeliveryFlux.subscribe();
        Thread.sleep(60000);

    }
    public static ConnectionFactory createFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.useNio();
        return connectionFactory;
    }
}
