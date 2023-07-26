package com.ifx.account.webflux.service;

import com.alibaba.fastjson2.JSON;
import com.ifx.account.AccountApplication;
import com.ifx.account.entity.ChatMsg;
import com.ifx.account.repository.ChatMsgRepository;
import com.ifx.account.service.ChatMsgService;
import com.ifx.account.vo.chat.PullChatMsgVo;
import com.ifx.account.vo.page.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/25
 */
@Slf4j
@SpringBootTest(classes = {AccountApplication.class})
public class ChatTest {

    @Autowired
    private ChatMsgRepository chatMsgRepository;

    @Autowired
    private ChatMsgService chatMsgService;

    @Test
    public void pullMsg() throws InterruptedException {

        Long msgId = 184l;
        PageVo pageVo = new PageVo(1, 100);
        PageRequest of = PageRequest.of(1, 100);
        PullChatMsgVo pullChatMsgVo = new PullChatMsgVo();
        pullChatMsgVo.setSessionId(msgId);
        pullChatMsgVo.setPageVo(pageVo);
        Assert.assertTrue(chatMsgService.pullMsgOrderByCreateTimeDesc(pullChatMsgVo).hasElements().block());


    }

    @Test
    public void pullMsg2() throws InterruptedException {
        Long sessionId = 1644223948273614848l;
        PageRequest of = PageRequest.of(1, 10);
        Sort descCreateTime = Sort.sort(ChatMsg.class).by(ChatMsg::getCreateTime).descending();
        log.info("args -- {} ", JSON.toJSONString(of));
        Assert.assertTrue(chatMsgRepository.findBySessionId(sessionId, of.withSort(descCreateTime)).hasElements().block());
    }


}
