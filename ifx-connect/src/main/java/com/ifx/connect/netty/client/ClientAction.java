package com.ifx.connect.netty.client;

import com.ifx.connect.proto.Protocol;
import io.netty.channel.ChannelFuture;

public interface ClientAction {

    public void init ();

    public void retry();

//    public void autoReConnect();

    public void resetConnect();
    /**
     * 发送信息机制
     * @param protocol
     */
    public Protocol sendJsonMsg(Protocol protocol);

    public ChannelFuture sent(String msg);

    /**
     * 心跳包机制
     */
    public void keepAlive();

    /**
     * 释放资源
     */
    public void releaseChannel();

    public void isClosed();

}
