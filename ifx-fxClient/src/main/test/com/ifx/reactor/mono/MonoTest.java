package com.ifx.reactor.mono;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/11
 */
@Slf4j
public class MonoTest {

    @Test
    public void test(){
        Assertions.assertThrows(NullPointerException.class, () -> Mono.just(null)
                .doOnNext(a -> Assert.notNull(a, "sd"))
                .doOnError(throwable -> log.info("出现异常"))
                .subscribe(s -> log.info(s.toString()))
        );
    }

    @Test
    public void switchIfEmpty(){
        ArrayList<Integer> integers = CollectionUtil.newArrayList(1, 2, 3);
        ArrayList<Integer> integer2 = CollectionUtil.newArrayList(1, 2, 3);
        Flux<Integer> wangpeng = Mono.just(integers)
                .flatMapMany(l-> Flux.fromIterable(l))
                .doOnNext(item -> integer2.remove(item))
                .thenMany(Flux.fromIterable(integer2))
                .switchIfEmpty(Flux.just(9,2,8,1,9,1))
                ;

        wangpeng.subscribe(s -> log.info("output {}",s));
            StepVerifier.create(wangpeng)
                    .expectNext(9,2,8,1,9,1)
                    .verifyComplete()
                    ;
//                    .verifyComplete();

    }
    @Test
    public void switchIfEm(){
        ArrayList<Integer> integers = CollectionUtil.newArrayList(1, 2, 3);
        ArrayList<Integer> integer2 = CollectionUtil.newArrayList(1, 2, 3);
        Flux<Integer> wangpeng = Mono.just(integers)
                .flatMapMany(l-> Flux.fromIterable(l))
                .doOnNext(item -> integer2.remove(item))
                .switchIfEmpty(Flux.just(9,2,8,1,9,1))
                .map(e->e+1)
                ;
        wangpeng.subscribe(k->log.info(k.toString()));
    }
    @Test
    public void switchIfEmMut(){
        ArrayList<Integer> integers = CollectionUtil.newArrayList(1, 2, 3);
        ArrayList<Integer> integer2 = CollectionUtil.newArrayList(1, 2, 3);
        Mono.just(integers)
                .flatMapMany(l-> Flux.fromIterable(l))
                .doOnNext(item -> integer2.remove(item))
                .switchIfEmpty(Flux.just(9,2,8,1,9,1))
//                .flatMap(e->Mono.empty())
                .doOnNext(s->log.info(s.toString()))
                .switchIfEmpty(Flux.just(567,2))
                .subscribe(k->log.info(k.toString()));
    }

    @Test
    public void empty(){
        String[] thy = new String[]{"222","4444"};
        Mono.justOrEmpty(Optional.ofNullable(null))
                .doOnNext(e->log.info(e.toString())).subscribe();
    }

    @Test
    public void subContext(){
        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .flatMap(s -> Mono.deferContextual(ctx ->
                        Mono.just(s + " " + ctx.get(key))))
                .contextWrite(ctx -> ctx.put(key, "World"));

        StepVerifier.create(r)
                .expectNext("Hello World")
                .verifyComplete();


    }




}
