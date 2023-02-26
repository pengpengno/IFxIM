package com.ifx.session.service;

import com.ifx.session.vo.ChatMsgVo;

import java.util.List;

/***
 * 消息发送
 */
public interface IChatAction {

    /**
     * <p>推送消息</p>
     * @param chatMsgVo  消息实体
     */
    public void pushMsg(ChatMsgVo chatMsgVo);  //写扩散



    /**
     * 拉取会话中最新消息
     * @param fromAccount 发送端账户
     * @param sessionId 会话Id
     */
    public List<ChatMsgVo> pullMsg(String fromAccount,Long sessionId);

    /**
     * 查询用户离线消息库
     * @param account
     */
    public List<ChatMsgVo> pullOffline(String account);

    /**
     * 拉取历史消息
     * @param sessionId
     */
    public List<ChatMsgVo> pullHisMsg(Long sessionId);



    public List<ChatMsgVo> pullHisMsgByQuery(Long sessionId);



}
