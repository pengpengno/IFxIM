package com.ifx.client.connect.event;

import com.ifx.connect.task.Task;

import java.util.concurrent.ConcurrentHashMap;

public class EventHandler {
//    TODO use to nio  Handler
    private ConcurrentHashMap<String, Task> taskMap ;

    public  void getTask(String protoColHeader){
        return taskMap.get(protoColHeader);
    }



}
