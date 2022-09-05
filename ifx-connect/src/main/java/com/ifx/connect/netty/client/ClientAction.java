package com.ifx.connect.netty.client;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.TaskHandler;

public interface ClientAction {


    /**
     * 发送信息机制
     * @param protocol
     */
    public void sendJsonMsg(Protocol protocol);

    public void sendJsonMsg(Protocol protocol, TaskHandler taskHandler);

    public void receive(Protocol protocol);






}
