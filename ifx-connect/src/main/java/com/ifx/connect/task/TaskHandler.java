package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;

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
