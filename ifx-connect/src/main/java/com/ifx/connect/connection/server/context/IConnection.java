package com.ifx.connect.connection.server.context;

import com.ifx.common.base.AccountInfo;
import com.ifx.connect.enums.ConnectionStatus;
import io.netty.channel.Channel;
import reactor.netty.Connection;

/**
 * 服务端使用的 connection 抽象接口
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
public interface IConnection {



    public Channel channel();

    public Connection connection();

    public AccountInfo accountInfo();

    public ConnectionStatus status();

    public void close();







}
