package com.ifx.account.service.impl;

import com.ifx.account.service.IChatAction;
import com.ifx.account.vo.ChatMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 消息
 */
@Service
@Slf4j
public class ChatAction implements IChatAction {




    @Override
    public List<ChatMsgVo> pullOffline(String account) {
        return null;
    }


    /***
     * 1.获取所属会话Id
     * 2. 尝试获取会话下用户状态
     * 3. 根据用户状态进行消息推送
     * 4. 写入消息推送日志
     * @param chatMsgVo  消息实体
     */
    @Override
    public void pushMsg(ChatMsgVo chatMsgVo) {
        final Long sessionId = chatMsgVo.getSessionId();

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
