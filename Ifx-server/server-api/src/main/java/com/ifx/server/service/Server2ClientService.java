package com.ifx.server.service;

import java.util.List;

/**
 *  <h1>服务器通知客户端服务
 */
public interface Server2ClientService {

    /**
     * 发送消息至客户端（不包含发送者）
     * @param toAccountId
     * @param msg
     */
    public void sendClient(String toAccountId,String msg );

    /**
     * 发送消息至客户段（包含发送者）
     * @param toAccountId 接收者
     * @param fromAccount 发送者
     * @param msg 发送消息
     */
    public void sendClient(String toAccountId, String fromAccount,String msg);

    /**
     * 批量推送消息
     * @param toAccountId
     * @param msgList
     */
    public void sendClientBatch(String toAccountId, List<String> msgList);



}
