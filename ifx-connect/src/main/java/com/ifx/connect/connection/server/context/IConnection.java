package com.ifx.connect.connection.server.context;

import com.ifx.common.base.AccountInfo;
import com.ifx.connect.enums.ConnectionStatus;
import io.netty.channel.Channel;
import reactor.netty.Connection;

import javax.validation.constraints.NotNull;

/**
 * 服务端使用的 connection 抽象接口
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
public interface IConnection {

    @NotNull(message = "connection could not be null , when put new connection in cache",groups = Create.class)
    public Channel channel();

    @NotNull(message = "connection could not be null",groups = Create.class)
    public Connection connection();

    @NotNull(message = "accountInfo could not be null",groups = Create.class)
    public AccountInfo accountInfo();

    public ConnectionStatus status();

    public void close();




    public static interface Create{

    }






}
