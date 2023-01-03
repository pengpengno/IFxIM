package com.ifx.connect.netty.client;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;

/**
 * 客户端基础行为
 */
public interface ClientAction {


    /**
     * 发送协议包{@link Protocol}
     * @param protocol
     */
    public void sendJsonMsg(Protocol protocol);

    /**
     * 发送 基于 回调的协议包 即 当任务在成功返回后将会执行{@link  TaskHandler}中的句柄任务
     * {@code  }
     * @param protocol
     * @param taskHandler
     */
    public void sendJsonMsg(Protocol protocol, TaskHandler taskHandler);



}
