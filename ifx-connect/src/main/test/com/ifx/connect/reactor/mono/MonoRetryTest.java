package com.ifx.connect.reactor.mono;



import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * @author pengpeng
 * @description
 * @date 2023/1/10
 */

public class MonoRetryTest {

    private static final Logger logger = LoggerFactory.getLogger(MonoRetryTest.class);

    private static BlockingQueue blockingQueue = new LinkedBlockingDeque(30);
    private static final List values = Collections.synchronizedList(new LinkedList<Integer>());

    @Test
    public void testMonoRetryWithBackOff() {

        Flux.range(0,2000)
                .publishOn(Schedulers.parallel())
                .flatMap(e -> monoUtil(e))
                .blockLast();

        int processedValues = values.size();
//        Assert.assertTrue("Signal not recieved for all elements", 2000 == processedValues);
    }

    private static Mono monoUtil(int val) {
        return Mono.just(1)
                .flatMap(e -> {
                    try {
                        blockingQueue.put(val);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    return serviceCall(Mono.just(val));
                })
                .doFinally(e -> {
                    blockingQueue.remove(val);
                    values.add(val);
                    logger.info("Finally Called "+ val);
                });
    }

    private static Mono serviceCall(Mono<Integer> responseMono) {
        return responseMono
                .map(response -> test(response))
                .retryWhen(Retry.backoff(3, Duration.ofMillis(10)))
                .onErrorReturn("Failed");
    }

    private static String test(Integer value) {
        if(value%5 == 0) throw new RuntimeException("Runtime Exception");
        return "Success";
    }
}
