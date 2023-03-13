package com.ifx.connect.connection.client;

import com.google.protobuf.Message;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
public interface ReactiveClientAction {


    Mono<Void> sendMessage(Message message);


    Mono<Void> sendString(String message);
}
