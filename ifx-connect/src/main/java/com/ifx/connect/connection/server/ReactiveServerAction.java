package com.ifx.connect.connection.server;

import com.google.protobuf.Message;
import com.ifx.connect.connection.server.context.IConnection;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/7
 */
public interface ReactiveServerAction {


    Mono<String>  sendString(IConnection connection,String message);

    Mono<String>  sendString(String account,String  message);

    Mono<Message>  sendMessage(String account , Message message);







}
