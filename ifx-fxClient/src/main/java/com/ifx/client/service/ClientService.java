package com.ifx.client.service;

import cn.hutool.core.util.IdUtil;
import com.ifx.client.task.TaskManager;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.TaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ClientService {

    @Resource
    private ClientAction clientAction;
    @Resource
    private TaskManager taskManager;
    public void send(Protocol protocol){
        String trace = IdUtil.fastSimpleUUID();
        protocol.setTrace(trace);
        clientAction.sendJsonMsg(protocol);
    }

    public void send(Protocol protocol, TaskHandler taskHandler){
        String trace = IdUtil.fastSimpleUUID();
        protocol.setTrace(trace);
        taskManager.addTask(trace,taskHandler);
        clientAction.sendJsonMsg(protocol);
    }
}
