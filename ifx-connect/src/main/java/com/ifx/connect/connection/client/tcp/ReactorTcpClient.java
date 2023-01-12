package com.ifx.connect.connection.client.tcp;

import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

/**
 * @author pengpeng
 * @date 2023/1/8
 */
public class ReactorTcpClient {

    private  static  Connection connection = null;
    public static  ReactorTcpClient create(){
        Connection conn = TcpClient.create()
//                .host()
                .connectNow();
        connection = conn;
        return null;
    }
}
