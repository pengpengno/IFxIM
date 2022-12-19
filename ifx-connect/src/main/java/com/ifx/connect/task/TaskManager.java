package com.ifx.connect.task;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
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

//    public static TaskHandler defaultTaskHandler = (Task) -> {log.info("执行心跳包！");};

    public static Integer TASK_MANAGER_SIZE = 100;   //  客户端总任务池大小
//     任务上下文管理器
//    private ConcurrentHashMap<String, ConcurrentLinkedDeque< > taskManager ;
//    private ConcurrentHashMap<String, ConcurrentLinkedDeque< ? extends TaskMeta>> taskManager ;
    private ConcurrentHashMap<String, ConcurrentLinkedDeque<TaskHandler>> taskManager ;


    public synchronized void init(){
        if (taskManager == null){
            log.info("正在初始化客户端核心任务管理器");
            synchronized (taskManager){
                taskManager = new ConcurrentHashMap<>(TASK_MANAGER_SIZE);
            }
        }
    }

    public void reset(){
        log.warn("正在重置客户端核心任务管理器");
        taskManager = new ConcurrentHashMap<>(TASK_MANAGER_SIZE);
    }


    public <T> ConcurrentLinkedDeque<TaskHandler> initTaskDeque(){
        log.info("正在初始化一个新任务队列");
        return  new ConcurrentLinkedDeque();
    }
//
//    public <T> ConcurrentLinkedDeque<TaskMeta> initTaskDeque(){
//        log.info("正在初始化一个新任务队列");
//        return  new ConcurrentLinkedDeque();
//    }

    private void attrTask(String key, TaskHandler value){
        init();   //初始化任务管理器
        taskManager.putIfAbsent(key, initTaskDeque());
        ConcurrentLinkedDeque<TaskHandler> taskHandlers = getTaskHandlers(key);
        assert taskHandlers != null : "任务管理异常！";
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
//    public TaskManager addTaskTaskMeta(String key, TaskHandler value) {
//        init();
//        ConcurrentLinkedDeque<TaskMeta> taskHandlers = taskManager.get(key);
//        if (taskHandlers == null) {
//            attrTask(key, value);
//        } else {
//            taskHandlers.addLast(value);
//        }
//        return this;
//    }

    /**
     *
     * @param key
     * @return
     */
    public ConcurrentLinkedDeque<TaskHandler> getTaskHandlers(String key){
        return taskManager.get(key);
    }


    /**
     * 处理
     * @param taskHandler
     * @param executor
     */
// 指定线程池处理
    public void doTask(TaskHandler taskHandler, ThreadPoolExecutor executor){
        executor.submit(()->taskHandler);
    }

    /**
     * 处理线程池
     * @param protocol
     */
    public void doTask(Protocol protocol){
        @SuppressWarnings("all")
        final Protocol pro = protocol;
        ConcurrentLinkedDeque<TaskHandler> taskHandlers = getTaskHandlers(pro.getTrace());
        if (CollectionUtil.isNotEmpty(taskHandlers)){
            while(!taskHandlers.isEmpty()){
                TaskHandler poll = taskHandlers.poll();
                poll.doTask(pro);
            }
        }
    }


// 指定协议处理
    public void doTask(TaskHandler taskHandler, Protocol protocol, ThreadPoolExecutor executor){
        executor.submit(()-> taskHandler.doTask(protocol));
    }


}
