package com.ifx.account.service;


import com.ifx.account.vo.ChatMsgVo;
import reactor.core.publisher.Mono;

import java.util.List;

/***
 * 消息发送
 */
public interface IChatAction {

    /**
     * <p>推送消息</p>
     * @param chatMsgVo  消息实体
     */
    public Mono<Void> pushMsg(ChatMsgVo chatMsgVo);



    /**
     * 拉取会话中最新消息
     * @param fromAccount 发送端账户
     * @param sessionId 会话Id
     */
    public List<ChatMsgVo> pullMsg(String fromAccount, Long sessionId);



    /**
     * 拉取历史消息
     * @param sessionId
     */
    public List<ChatMsgVo> pullHisMsg(Long sessionId);



    public List<ChatMsgVo> pullHisMsgByQuery(Long sessionId);



}
