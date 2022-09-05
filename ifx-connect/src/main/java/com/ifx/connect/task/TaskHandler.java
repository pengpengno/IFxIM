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

    default void init(){

    }

    default void release(){
    }





}
