package com.ifx.connect.connection.client;

import reactor.core.publisher.Mono;

import java.nio.channels.Channel;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
public interface ReactorClient {



    public Mono<Channel> channel();



}
