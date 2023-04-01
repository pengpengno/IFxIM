package com.ifx.account.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
public class ReactorSubscribeOn {
    @Test
    public void subScribeOn() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i)
                .subscribeOn(s)
                .map(i -> "value " + i);

        new Thread(() -> flux.subscribe(System.out::println));
        Thread.sleep(1000);
    }

}
