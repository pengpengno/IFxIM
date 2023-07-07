package com.ifx.connect.reactor.mono;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
@Slf4j
public class MonoShareTest {

    @Test
    public void share(){
        Mono<String> share = Mono.just("test")
                .doOnNext(s -> log.info("share --- {}",s))
                .share();

        Mono<String> test = Mono.just("test")
                .doOnNext(s -> log.info("test---{}",s));


        log.info("test =======");
        test.subscribe();
        test.subscribe();

        log.info("share =======");
        share.subscribe();
        share.subscribe();
    }


    @Test
    public void publish(){
        Mono<String> publish = Mono.just("test")
                .doOnNext(s -> log.info("publish --- {}",s))
                .publish(mo -> mo.map(e->e+"add String"))
                .doOnNext(s -> log.info("publish --- {}",s))
                ;
        Mono<String> map = Mono.just("test")
                .doOnNext(s -> log.info("publish --- {}", s))
                .publish(mo -> mo.map(e -> e + "add String"))
                .map(s -> s + "add String");

        Mono<String> test = Mono.just("test")
                .doOnNext(s -> log.info("test --- {}",s));

        log.info("test =======");
        test.subscribe();
        test.subscribe();

        log.info("publish =======");
        publish.subscribe();
        publish.subscribe();
        publish.subscribe();
    }
}
