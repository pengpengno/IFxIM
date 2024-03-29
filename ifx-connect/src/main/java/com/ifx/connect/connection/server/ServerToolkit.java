package com.ifx.connect.connection.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ifx.connect.connection.server.context.IConnectContext;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.tcp.ReactorTcpAction;
import com.ifx.connect.connection.server.tcp.ReactorTcpServer;
import com.ifx.connect.module.ConnectionModule;

/**
 * server 工具包
 * @author pengpeng
 * @description
 * @date 2023/3/6
 */
public class ServerToolkit {

    private static final Injector inject = Guice.createInjector(new ConnectionModule());



    public static IConnectContextAction contextAction(){
//        return inject.getInstance(IConnectContextAction.class);
        return IConnectContext.getInstance();
    }

    public static ReactiveServerAction reactorTcpServerAction(){
        return ReactorTcpAction.getInstance();
    }


    public static ReactiveServer reactiveServer(){
        return ReactorTcpServer.getInstance();
    }

}
