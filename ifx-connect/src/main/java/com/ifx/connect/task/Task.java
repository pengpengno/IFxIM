package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;

import java.util.function.Consumer;

@FunctionalInterface
public interface Task  extends Consumer<Protocol>{
    @Override
    void accept(Protocol o);

    default void doTask(Protocol protocol){
        accept(protocol);
    }

}
