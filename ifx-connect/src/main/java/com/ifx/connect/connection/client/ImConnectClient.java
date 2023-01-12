package com.ifx.connect.connection.client;

import reactor.netty.Connection;

/**
 * @author pengpeng
 * @date 2023/1/9
 */
public interface ImConnectClient {

    public Connection getConnect();
}
