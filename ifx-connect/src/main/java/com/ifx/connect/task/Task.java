package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;

@FunctionalInterface
public interface Task  {

    public void doTask(Protocol protocol);

}
