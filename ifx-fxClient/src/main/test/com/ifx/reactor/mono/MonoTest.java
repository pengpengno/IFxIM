package com.ifx.reactor.mono;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

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
}
