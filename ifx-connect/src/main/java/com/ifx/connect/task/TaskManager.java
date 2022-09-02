package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 任务执行的要素
 * 执行时间 执行器 执行逻辑 执行协议实体  执行链 执行结果 执行回调
 */
@Slf4j
@Component
public class TaskManager {
    public static Task DEFAULT_TASK = (Task) -> {log.info("默认的 心跳包！");};

    private ConcurrentHashMap<String, List<Task>> taskMap  = new ConcurrentHashMap<>();
//     任务上下文管理器
    private ConcurrentHashMap<String, ConcurrentLinkedDeque<Task>> taskManager  = new ConcurrentHashMap<>();

    private ConcurrentLinkedDeque<Task> taskLinkedDeque = new ConcurrentLinkedDeque<>();

    public void attrTask(String key,Object value){

    }
//指定线程处理
    public void doTask(Task task,Thread thread){

    }


// 指定线程池处理
    public void doTask(Task task, ThreadPoolExecutor executor){
//        executor.submit()
    }
// 指定协议处理
    public void doTask(Task task, Protocol protocol,ThreadPoolExecutor executor){
        executor.submit(()->task.doTask(protocol));
    }




}
