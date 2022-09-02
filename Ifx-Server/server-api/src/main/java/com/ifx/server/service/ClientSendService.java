package com.ifx.server.service;

import java.util.List;

/**
 *  与客户端之间的交互
 */
public interface ClientSendService {

    /**
     * 发送消息至客户端
     * @param accountId
     * @param msg
     */
    public void sendClient(String accountId,String msg );

    public void sendClientBatch(String accountId, List<String> msgs);


//    public void delaySendClient(String accountId,)


}
