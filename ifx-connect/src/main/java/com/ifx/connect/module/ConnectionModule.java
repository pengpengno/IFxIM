package com.ifx.connect.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.context.Con;
import com.ifx.connect.connection.server.context.IConnectContext;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.connection.server.tcp.ReactorTcpServer;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/6
 */
public class ConnectionModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IConnectContextAction.class).to(IConnectContext.class).in(Singleton.class);
        bind(IConnection.class).to(Con.class);
        bind(ReactiveServer.class).annotatedWith(Names.named("ReactorTcpServer")).toInstance(ReactorTcpServer.getInstance());
        // other bindings here
    }
}
