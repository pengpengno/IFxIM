package com.ifx.account.controller;

import com.ifx.account.chat.ChatRoute;
import com.ifx.account.vo.ChatMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
@RestController
@RequestMapping(ChatRoute.CHAT_ROUTE)
@Slf4j
@Validated
public class ChatController {



    @PostMapping(ChatRoute.CHAT)
    public Mono<Boolean>  sendMsg(@RequestBody ChatMsgVo chatMsgVo){
        return null;
    }



}