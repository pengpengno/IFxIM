package com.ifx.reactor;

import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pengpeng
 * @date 2023/1/5
 */
@Slf4j
public class TestCase {

    @Test
    public void flux(){
        Flux.range(1,3).subscribe(System.out::println);
    }
    @Test
    public void fluxs(){
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error: " + error));
    }
    @Test
    public  void flux3() {
        log.info("start");
        Flux<Integer> ints = Flux.range(1, 4);
        Disposable done = ints.subscribeOn(Schedulers.single())
                .subscribe(i -> log.info(String.valueOf(i))
            ,
                error -> log.info("Error " + error),
                () -> log.info("Done"));
        System.out.println(done.isDisposed());
    }

    /**
     * flux4 generate
     */
    @Test
    public void flux4 (){
//        Flux<String> flux = Flux.generate(
//                () -> 0,
//                (state, sink) -> {
//                    sink.next("3 x " + state + " = " + 3*state);
//                    if (state == 10) sink.complete();
//                    return state + 1;
//                });
//        flux.subscribe(s->log.info(s.toString()));

        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    String next = "3 x " + i + " = " + 3*i;
                    log.info("发送 case {}" ,next);
                    sink.next(next);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));
        flux.subscribe(log::info);
    }
    @Test
    public void star (){
        DisposableServer server =
                TcpServer.create()
                        .doOnConnection(conn ->
                                conn.addHandlerFirst(new ReadTimeoutHandler(10, TimeUnit.SECONDS)))
                        .doOnChannelInit((observer, channel, remoteAddress) ->
                                channel.pipeline()
                                        .addFirst(new LoggingHandler("reactor.netty.examples")))
                        .bindNow();
        server.onDispose()
                .block();
    }

    @Test
    public void test(){
        AtomicInteger errorCount = new AtomicInteger();
        Flux<String> flux = Flux.<String>error(new IllegalStateException("boom"))
                .doOnError(e -> {
                    errorCount.incrementAndGet();
                    System.out.println(e + " at " + LocalTime.now());
                })
                .retryWhen(Retry
                        .backoff(3, Duration.ofMillis(100))
                        .jitter(0d)
                        .doAfterRetry(rs -> System.out.println("retried at " + LocalTime.now() + ", attempt " + rs.totalRetries()))
                        .onRetryExhaustedThrow((spec, rs) -> rs.failure())
                );
//        flux.subscribe();

        StepVerifier.create(flux)
                .expectError(IllegalStateException.class)
                .verify();

        assertEquals(4, errorCount.get());
    }

}
