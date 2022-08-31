package com.ifx.server.invoke;

import com.ifx.connect.proto.Protocol;
import io.netty.channel.ChannelHandlerContext;

public interface GateInvoke {


    public void doWork(ChannelHandlerContext channel, Protocol protocol);

    public void doException(Throwable e);




}
