package com.ifx.account.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/28
 */
public class FluxLimitRate {
    @Test
    public  void limitRate() throws InterruptedException {
    int num =100;
        Flux<Object> objectFlux = Flux.create(sink -> {
            for (int i = 0; i < num; i++) {
                sink.next("i" + i);
            }
            sink.complete();
        });
        Flux<String> range = Flux.range(0, 100).map(e-> e+"");
        Flux<String> flux = processItems(range)
                ;
        flux.subscribe(e-> {
            System.out.println(e);
        });
        Thread.sleep(4000);
    }


    @Test
    public void limit() throws InterruptedException {
        Flux.range(1, 100)
                .log()
                .limitRate(10)
                .delayElements(Duration.ofMillis(100))
                .subscribe(System.out::println);
        Thread.sleep(3000);
    }
    public Flux<String> processItems(Flux<String> items) {
        return items
                .limitRate(10)
                .delayElements(Duration.ofMillis(100))
                .map(this::processItem).log();
    }

    private  String processItem(String item) {
        // Process the item
        return "Processed " + item;
    }
}
