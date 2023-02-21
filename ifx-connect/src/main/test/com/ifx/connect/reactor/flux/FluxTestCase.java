package com.ifx.connect.reactor.flux;

import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.exec.ex.net.NetException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/10
 */
@Slf4j
public class FluxTestCase {
    @Test
    public void error(){
        AtomicInteger errorCount = new AtomicInteger();
        Flux<String> flux =
                Flux.<String>error(new NetException("boom"))
                        .doOnError(e -> {
                            errorCount.incrementAndGet();
                            log.info(e + " at  " + LocalTime.now() + errorCount.get());
                        })
                        .doAfterTerminate(()-> log.info("terminate"))
                        .retryWhen(
                                Retry
                                .backoff(3, Duration.ofSeconds(1)).jitter(0.3d)
                                        .filter(throwable -> throwable instanceof NetException)
                                .doAfterRetry(rs -> log.info("retried at " + LocalTime.now() + ", attempt " + rs.totalRetries()))
                                .onRetryExhaustedThrow((spec, rs) -> rs.failure())
                        );
        StepVerifier
            .create(flux)
//                .verifyComplete()
                .verifyError()

                ;
//                .expectError()
//            .expectError(Exception.class)
//            .verify()
        ;
        Assertions.assertEquals(errorCount.get(),4);

    }


@Test
public void startClient(){
    Mono.just(ClientToolkit.getDefaultClientLifeStyle())
            .doOnNext(l-> {
                InetSocketAddress inetSocketAddress =
                        new InetSocketAddress("127.0.0.1", 8094);
                l.reTryConnect(inetSocketAddress);
            })
            .subscribe()
    ;
}

    public static void main(String[] args) {

        AtomicInteger errorCount = new AtomicInteger();
        Flux<String> flux =
                Flux.<String>error(new IllegalStateException("boom"))
                        .doOnError(e -> {
                            errorCount.incrementAndGet();
                            System.out.println(e + " at " + LocalTime.now());
                        })
                        .retryWhen(Retry
                                .fixedDelay(3, Duration.ofMillis(291))
//                                .jitter(0d)
                                .doAfterRetry(rs -> System.out.println("retried at " + LocalTime.now() + ", attempt " + rs.totalRetries()))
                                .onRetryExhaustedThrow((spec, rs) -> rs.failure())
                        );
        flux.subscribe();
    }


    @Test
    public void retryWhen3(){
        AtomicInteger errorCount = new AtomicInteger();
        Flux<String> flux =
                Flux.<String>error(new IllegalArgumentException())
                        .doOnError(e -> errorCount.incrementAndGet())
                        .retryWhen(Retry.from(companion ->
//                                companion.handle((retrySignal, synchronousSink) -)
                                companion.map(rs -> {
                                    if (rs.totalRetries() < 3) {
                                        log.info("重置 {}",rs.totalRetries());
                                        return rs.totalRetries();
                                    }
                                    else throw Exceptions.propagate(rs.failure());
                                })
                        )
                        );

        flux.subscribe();
    }

}

