package com.ifx.server.invoke;

import com.ifx.connect.proto.Protocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * 网关路由命令解析处理层
 * <ul>
 *     <li>dubbo解析转发</li>
 *     <li>Ifx指令解析转发</li>
 * </ul>
 *
 */
public interface GateInvoke {


    public void doWork(ChannelHandlerContext channel, Protocol protocol);

    public void doException(Throwable e);




}
