package com.ifx.client.service;

import com.ifx.connect.task.handler.TaskHandler;

import java.util.function.Function;

/**
 * 客户端任务处理抽象接口
 * @author pengpeng
 * @description
 * @date 2023/1/16
 */
@FunctionalInterface
public interface IMBiFunction {
    /***
     * for callback ,  push the taskHandler  in  CallBackChain  , to process  program in a non-blocking status
     * the   proxyService  which is remote service , as general , it will block program, and  IMBiFunction
     * will assembly process-chain , and  make sure process Program not blocking ;
     * @see com.ifx.client.ann.ProxyService
     * @see com.ifx.connect.task.TaskManager
     * @param proxyService
     * @param taskHandler
     */
    public void call(Function<?,?> proxyService, TaskHandler taskHandler);

}
