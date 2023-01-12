package com.ifx.connect.reactor.flux;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

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
                Flux.<String>error(new Exception("boom"))
                        .doOnError(e -> {
                            errorCount.incrementAndGet();
                            log.info(e + " at  " + LocalTime.now() + errorCount.get());
                        })
                        .doAfterTerminate(()-> log.info("erminate"))
                        .retryWhen(
                                Retry
                                .backoff(10, Duration.ofSeconds(1)).jitter(0.3d)
                                .doAfterRetry(rs -> log.info("retried at " + LocalTime.now() + ", attempt " + rs.totalRetries()))
                                .onRetryExhaustedThrow((spec, rs) -> rs.failure())
                        );

        flux.subscribe(s-> Flux
                .just(s)
                .doOnError(errors -> log.error("sss{}", ExceptionUtil.stacktraceToString(errors)))
                .subscribe()
        );
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

