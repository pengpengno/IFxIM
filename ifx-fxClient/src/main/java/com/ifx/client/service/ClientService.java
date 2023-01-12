package com.ifx.client.service;

import cn.hutool.core.util.IdUtil;
import com.ifx.common.constant.CommonConstant;
import com.ifx.connect.task.TaskManager;
import com.ifx.connect.connection.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import com.ifx.connect.task.meta.TaskMeta;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @see ClientAction
 * 客户端服务
 */
@Service
@Slf4j
@Deprecated
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

    /**
     * <P>客户端发送通讯协议包{@link Protocol}
     * 并将{@link TaskHandler } 存入 {@link TaskManager } 任务管理池中
     * @param protocol 协议包
     * @param taskHandler 任务回调执行基本单元
     */
    @Deprecated
    public void send(Protocol protocol, TaskHandler taskHandler){
//       保存 TaskHandler等待回调
        String trace = IdUtil.fastSimpleUUID();
        log.debug("生成的 trace 为 {}",trace);
        MDC.put(CommonConstant.MDC_TRACE_ID,trace);
        protocol.setTrace(trace);
        taskManager.addTask(trace,taskHandler);
        clientAction.sendJsonMsg(protocol);
    }

    public void defaultSend(){

    }


    // TODO 任务元数据 使用
    public void send(Protocol protocol, TaskMeta taskMeta){
//       保存 TaskHandler等待回调
        String trace = IdUtil.fastSimpleUUID();
        protocol.setTrace(trace);
//        taskManager.addTask(trace,taskHandler);
        clientAction.sendJsonMsg(protocol);
    }
}
