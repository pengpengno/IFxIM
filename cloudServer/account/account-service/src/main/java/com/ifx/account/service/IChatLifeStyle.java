package com.ifx.account.service;


import com.ifx.account.vo.ChatMsgVo;

/***
 *
 */
public interface IChatLifeStyle {


    public void init();

    /**
     * 消息发送
     * @param chatMsgVo
     * @return
     */
    public Boolean sent(ChatMsgVo chatMsgVo);

    /**
     * 消息重发
     * @param chatMsgVo
     * @return
     */
    public Boolean reSent(ChatMsgVo chatMsgVo);

    /**
     * 消息派送成功后 持久化
     * @param chatMsgVo
     * @return
     */
    public Boolean store(ChatMsgVo chatMsgVo);

    /**
     * 消息的离线存储
     * @param chatMsgVo
     * @return
     */
    public Boolean offlineStore(ChatMsgVo chatMsgVo);


}
