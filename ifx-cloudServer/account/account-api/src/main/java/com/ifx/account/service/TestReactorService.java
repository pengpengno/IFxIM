package com.ifx.account.service;

import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/8
 */
public interface TestReactorService {
    Mono<Long>  getMonoLong(Long l);
}
