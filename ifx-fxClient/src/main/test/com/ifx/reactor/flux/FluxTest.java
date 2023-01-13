package com.ifx.reactor.flux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/13
 */
@Slf4j
public class FluxTest {

    @org.junit.jupiter.api.Test
    public void  hot(){
        Sinks.Many<String> hotSource = Sinks.unsafe().many().multicast().directBestEffort();

        Flux<String> hotFlux = hotSource.asFlux().map(String::toUpperCase);

        hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: "+d));

        hotSource.emitNext("blue", FAIL_FAST);
        hotSource.tryEmitNext("green").orThrow();

        hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: "+d));

        hotSource.emitNext("orange", FAIL_FAST);
        hotSource.emitNext("purple", FAIL_FAST);
        hotSource.emitComplete(FAIL_FAST);
    }

    @Test
    public void  sample (){
        Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .map(String::toUpperCase);

        source.subscribe(d -> System.out.println("Subscriber 1: "+d));
        source.subscribe(d -> System.out.println("Subscriber 2: "+d));
    }
    @Test
    public void boarsting() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("subscribed to source"));

        ConnectableFlux<Integer> co = source.publish();

        co.subscribe(e->log.info(e.toString()), e -> {}, () -> {});
        co.subscribe(e->log.info(e.toString()), e -> {}, () -> {});

        System.out.println("done subscribing");
        Thread.sleep(500);
        System.out.println("will now connect");

        co.connect();
    }
    @Test
    public void autoconn () throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("subscribed to source"));

        Flux<Integer> autoCo = source.publish().autoConnect(2);

        autoCo.subscribe(System.out::println, e -> {}, () -> {});
        log.info("subscribed first");
        Thread.sleep(500);
        log.info("subscribing second");
        autoCo.subscribe(System.out::println, e -> {}, () -> {});

    }

    @Test
    public void subli(){
        Flux<String> source = Flux.just("a", "b", "c");
        source.subscribe(new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(1); // <-- here
            }

            @Override
            protected void hookOnNext(String value) {
                request(1); // <-- here
                System.out.println(value);
            }
        });
    }

    @Test
    public void windows(){
        StepVerifier.create(
                        Flux.range(1, 10)
                                .window(5, 3) //overlapping windows
                                .concatMap(g -> g.defaultIfEmpty(-1)) //show empty windows as -1
                )
                .expectNext(1, 2, 3, 4, 5)
                .expectNext(4, 5, 6, 7, 8)
                .expectNext(7, 8, 9, 10)
                .expectNext(10)
                .verifyComplete();
    }
}
