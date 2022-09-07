package com.ifx.server.service;

import java.util.List;

/**
 *  与客户端之间的交互
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
     * @param formAccount 发送者
     * @param msg
     */
    public void sendClient(String toAccountId, String formAccount,String msg);


    public void sendClientBatch(String toAccountId, List<String> msgList);



}
