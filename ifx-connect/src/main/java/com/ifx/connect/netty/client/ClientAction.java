package com.ifx.connect.netty.client;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.Task;
import io.netty.channel.ChannelFuture;

public interface ClientAction {

    public void connect();

    public void reConnect();

    public void init ();

    public void retry();

//    public void autoReConnect();

    public void resetConnect();
    /**
     * 发送信息机制
     * @param protocol
     */
    public Protocol sendJsonMsg(Protocol protocol);

    public Protocol sendJsonMsg(Protocol protocol, Task task);
    public Protocol sendJsonMsg(Protocol protocol, Task task,Thread thread);

    public Task getTask(Protocol protocol);

    public Protocol doBioReq(Protocol protocol);

    public ChannelFuture sent(String msg);

    /**
     * 心跳包机制
     */
    public void keepAlive();

    /**
     * 释放资源
     */
    public void releaseChannel();

    public Boolean isActive();

//
//    /**=== 请求模式 是否 归属 ClientAction 模块？？ ==*/
//    public void oneWay();
//
//    public void twoWay();

}
