package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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

    public void attrTask(String key,Object value){

    }

    public void doTask(Task task,Thread thread){

    }

    public void doTask(Task task, ThreadPoolExecutor executor){
//        executor.submit()
    }

    public void doTask(Task task, Protocol protocol,ThreadPoolExecutor executor){
        executor.submit(()->task.doTask(protocol));
    }
}