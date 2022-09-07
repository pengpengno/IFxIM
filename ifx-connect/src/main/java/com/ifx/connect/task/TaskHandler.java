package com.ifx.connect.task;

import cn.hutool.extra.spring.SpringUtil;
import com.ifx.connect.proto.Protocol;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@FunctionalInterface
public interface TaskHandler extends Consumer<Protocol> {


    @Override
    void accept(Protocol o);

    default void doTask(Protocol protocol){
        accept(protocol);
    }

    /**
     * 预留初始化 Task
     */
    default void init(){

    }

    /**
     * 任务执行前置操作
     */
    default void prevDoWork(){

    }

    /**
     *
     * 释放 Task 的操作
     */
    default void release(){
    }





}
