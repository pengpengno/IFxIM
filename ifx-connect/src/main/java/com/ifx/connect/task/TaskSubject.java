package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;

@Component
public class TaskSubject {
    private DelayQueue taskList = new DelayQueue();

    public  void attach(Protocol protocol){
//        taskList.poll()
    }


}
