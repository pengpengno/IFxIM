package com.ifx.account.service;

import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
public interface IMessageLifeStyle {


    Mono<Long>  init();



    Mono<Long>  sent();



}
