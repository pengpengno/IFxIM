package com.ifx.session.service;

import com.ifx.session.vo.ChatMsgVo;

import java.util.List;

public interface IChatAction {


    public void pushMsg(String fromAccount,Long sessionId,String msg);

    public void pushMsg(ChatMsgVo chatMsgVo);


    /**
     * 拉取会话中最新消息
     * @param fromAccount
     * @param sessionId
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






}
