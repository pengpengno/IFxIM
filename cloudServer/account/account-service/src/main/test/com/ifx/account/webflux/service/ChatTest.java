package com.ifx.account.webflux.service;

import com.ifx.account.AccountApplication;
import com.ifx.account.entity.ChatMsgRe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {AccountApplication.class})
@Slf4j
public class ChatTest {
    @Autowired
    ChatMsgRecordRepository chatMsgRecordRepository;


    @Test
    public  void  chatSave(){
        ChatMsgRe chatMsgRe = new ChatMsgRe();
        chatMsgRe.setMsgId(2l);
        chatMsgRe.setSessionId(1111l);
        chatMsgRecordRepository.save(chatMsgRe).subscribe();
    }
}
