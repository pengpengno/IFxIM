package com.ifx.connect.reactor.mono;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
@Slf4j
public class ReactorSubscribeOn {
    @Test
    public void subScribeOn() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {log.info("upstream");return 10 + i;})
                .subscribeOn(s)
                .map(i -> {log.info("downstream"); return "value " + i;});
        flux.subscribe(e->log.info(e));
//        new Thread(() -> flux.subscribe(e->log.info(e)));
        StepVerifier.create(flux)
                        .expectComplete();
//        Thread.sleep(1000);
    }

    @Test
    public void publishOn(){
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {log.info("upstream1");return 10 + i;})
                .subscribeOn(s)

                .map(i -> {log.info("upstream2");return 10 + i;})

                .publishOn(s)
                .map(i -> "value " + i)
                .map(i -> {log.info("downstream");return 10 + i;})

                ;

        flux.subscribe(e->log.info(e));

        StepVerifier.create(flux)
                .expectComplete();
    }

}
