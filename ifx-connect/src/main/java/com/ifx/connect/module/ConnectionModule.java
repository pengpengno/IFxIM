package com.ifx.connect.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.ifx.connect.connection.server.context.Con;
import com.ifx.connect.connection.server.context.IConnectContext;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;

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
        // other bindings here
    }
}
