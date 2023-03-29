package com.ifx.connect.connection.server;

import com.google.inject.Inject;
import com.ifx.connect.connection.server.tcp.ReactorTcpServer;

import java.net.InetSocketAddress;

/**
 * 流式 server
 * @author pengpeng
 * @description
 * @date 2023/2/28
 */
public interface ReactiveServer {

    @Inject
    public default ReactiveServer getInstance(ReactorTcpServer tcpServer){
        return tcpServer;
    }

    public void init (InetSocketAddress address);


    public void start();


    public void stop();




}
