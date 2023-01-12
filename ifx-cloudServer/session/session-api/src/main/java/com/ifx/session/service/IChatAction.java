package com.ifx.session.service;

import com.ifx.session.vo.ChatMsgVo;

import java.util.List;

public interface IChatAction {

    /**
     * <h1> 推送消息
     * <p>使用写扩散实现消息的推送</p>
     *
     * @param fromAccount 发送端账户
     * @param sessionId 会话标识
     * @param msg 消息体
     */
    public void pushMsg(String fromAccount,Long sessionId,String msg); // 通讯模式【写扩散】设计实现

    public void pushMsg(ChatMsgVo chatMsgVo); //写扩散


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


//    TODO 条件查询
    public List<ChatMsgVo> pullHisMsgByQuery(Long sessionId);



}
