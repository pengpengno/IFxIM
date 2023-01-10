package com.ifx.connect.connection.client;

import com.ifx.connect.connection.client.tcp.NettyClientAction;

/**
 * @author pengpeng
 * @date 2023/1/9
 */
public  class ClientDomainHelper {

    /**
     * 获取 Tcp 连接对象
     * @return
     */
    public static ClientAction getTcpInstance(){
        return NettyClientAction.getInstance();
    }
}
