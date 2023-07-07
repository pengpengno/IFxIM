package com.ifx.account.service.impl;

import com.ifx.account.repository.ChatMsgRepository;
import com.ifx.account.service.IMessageLifeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
public class MessageLifeStyle implements IMessageLifeStyle {


    @Autowired
    private ChatMsgRepository chatMsgRepository;

    @Override
    public Mono<Long> init() {

        return null;
    }

    @Override
    public Mono<Long> sent() {
        return null;
    }
}
