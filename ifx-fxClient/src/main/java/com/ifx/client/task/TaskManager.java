package com.ifx.client.task;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.TaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    public static TaskHandler defaultTaskHandler = (Task) -> {log.info("执行心跳包！");};

    public static Integer TASK_MANAGER_SIZE = 20;   //  客户端总任务池大小
//    public static Integer TASK_LINKED_DEQUE = 20;   //  任务队列长度
//     任务上下文管理器
    private ConcurrentHashMap<String, ConcurrentLinkedDeque<TaskHandler>> taskManager ;

//    private ConcurrentLinkedDeque<TaskHandler> taskHandlerLinkedDeque;

    public void init(){
        if (taskManager == null){
            log.info("正在初始化客户端任务管理器");
            taskManager = new ConcurrentHashMap<>(TASK_MANAGER_SIZE);
        }
    }

    public void reset(){
        log.warn("正在重置客户端任务管理器");
        taskManager = new ConcurrentHashMap<>(TASK_MANAGER_SIZE);
    }
    public ConcurrentLinkedDeque<TaskHandler> initTaskDeque(){
        log.info("正在初始化一个新任务队列");
        return  new ConcurrentLinkedDeque<TaskHandler>();
    }

    private void attrTask(String key, TaskHandler value){
         init();
         taskManager.putIfAbsent(key, initTaskDeque());
        ConcurrentLinkedDeque<TaskHandler> taskHandlers = getTaskHandlers(key);
        assert taskHandlers != null;
        taskHandlers.addLast(value);
    }

    public void addTaskHandler(String key, TaskHandler value){
        addTask(key,value);
    }

    public TaskManager addTask(String key, TaskHandler value) {
        init();
        ConcurrentLinkedDeque<TaskHandler> taskHandlers = taskManager.get(key);
        if (taskHandlers == null) {
            attrTask(key, value);
        } else {
            taskHandlers.addLast(value);
        }
        return this;
    }

    public ConcurrentLinkedDeque<TaskHandler> getTaskHandlers(String key){
        return taskManager.get(key);
    }





// 指定线程池处理
    public void doTask(TaskHandler taskHandler, ThreadPoolExecutor executor){
        executor.submit(()->taskHandler);
    }
    public void doTask(Protocol protocol){
        ConcurrentLinkedDeque<TaskHandler> taskHandlers = getTaskHandlers(protocol.getTrace());
        if (CollectionUtil.isNotEmpty(taskHandlers)){
            while(!taskHandlers.isEmpty()){
                taskHandlers.poll().doTask(protocol);
            }
        }
    }


// 指定协议处理
    public void doTask(TaskHandler taskHandler, Protocol protocol, ThreadPoolExecutor executor){
        executor.submit(()-> taskHandler.doTask(protocol));
    }


}
