package com.ifx.account.service.impl;

import com.ifx.account.service.ChatMsgService;
import com.ifx.account.service.IChatAction;
import com.ifx.account.service.ISessionLifeStyle;
import com.ifx.account.vo.ChatMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/***
 * 消息
 */
@Service
@Slf4j
public class ChatAction implements IChatAction {

    @Autowired
    private ISessionLifeStyle sessionLifeStyle;


    @Autowired
    ChatMsgService chatMsgService;



    /***
     * <p>存储消息</p>
     * <p>获取所属会话Id
     * <p>尝试获取会话下用户状态
     * <p>根据用户状态进行消息推送
     * <p>写入消息推送日志
     * @param chatMsgVo  消息实体
     */
    @Override
    public void pushMsg(ChatMsgVo chatMsgVo) {
        chatMsgService.saveMsg(chatMsgVo)
                .then(Mono.just(chatMsgVo))
                .map(e->e.getSessionId())
                .map(e-> e)

        ;

    }

    @Override
    public List<ChatMsgVo> pullMsg(String fromAccount, Long sessionId) {
        return null;
    }


    @Override
    public List<ChatMsgVo> pullHisMsg(Long sessionId) {
        return null;
    }


    @Override
    public List<ChatMsgVo> pullHisMsgByQuery(Long sessionId) {
        return null;
    }
}
