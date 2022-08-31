package com.ifx.server.service;

public interface ClientSendService {

    /**
     * 发送消息至客户端
     * @param accountId
     * @param msg
     */
    public void sendClient(String accountId,String msg );


}
