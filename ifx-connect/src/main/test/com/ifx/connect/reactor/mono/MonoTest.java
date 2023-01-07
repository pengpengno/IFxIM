package com.ifx.connect.reactor.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * Mo
 * @author pengpeng
 * @date 2023/1/7
 */
public class MonoTest {

    @Test
    public void test(){
        Mono.just(1)
                .map(integer -> String.valueOf(integer)+"su")
                .flatMap(bu -> Mono.just(Boolean.TRUE))
                .subscribe(System.out::println);
    }
}
