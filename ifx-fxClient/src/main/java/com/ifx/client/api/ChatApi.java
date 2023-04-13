package com.ifx.client.api;

import com.ifx.connect.connection.client.ReactiveClientAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatApi {

    ReactiveClientAction clientAction;


//    public void sendMessage(String message , Long sessionId , String account){
//
//
//        clientAction.sendMessage()
//    }
}
