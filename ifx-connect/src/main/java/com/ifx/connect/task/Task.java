package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@FunctionalInterface
public interface Task  extends Consumer<Protocol> {
    @Override
    void accept(Protocol o);

    public ConcurrentHashMap<String,Object> attrTaskMap = new ConcurrentHashMap<>();


    default void attrKey(String key,Object value){
        attrTaskMap.put(key, value);
    }

    default Object getAttrValue(String key){
        return attrTaskMap.get(key);
    }

    default void doTask(Protocol protocol){
        accept(protocol);
    }

}
