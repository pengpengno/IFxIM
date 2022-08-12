package com.ifx.server.invoke;

import com.ifx.connect.proto.Protocol;
import io.netty.channel.ChannelHandlerContext;

import java.nio.channels.Channel;

public interface GateInvoke {

    public void doWork(ChannelHandlerContext channel, Protocol protocol);
}
