package com.ifx.connect.handler;

import com.ifx.connect.connection.server.context.IConnection;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/10
 */
@FunctionalInterface
public interface MessageProcess {


    public void process(IConnection connection );



}
