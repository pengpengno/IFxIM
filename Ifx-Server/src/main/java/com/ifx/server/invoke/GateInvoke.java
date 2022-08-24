package com.ifx.server.invoke;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ProtocolHeaderConst;
import com.ifx.server.invoke.dubbo.DubboInvoke;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;
import java.nio.channels.Channel;

public interface GateInvoke {


    public void doWork(ChannelHandlerContext channel, Protocol protocol);

    public void doException(Throwable e);




}
