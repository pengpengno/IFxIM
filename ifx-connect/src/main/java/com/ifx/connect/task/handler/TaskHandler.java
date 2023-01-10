package com.ifx.connect.task.handler;

import com.ifx.connect.proto.Protocol;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * 任务执行基本单元
 */
@FunctionalInterface
public interface  TaskHandler extends Consumer<Protocol> {

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
     *
     * 释放 Task 的操作
     */
    default void release(){
    }





}
