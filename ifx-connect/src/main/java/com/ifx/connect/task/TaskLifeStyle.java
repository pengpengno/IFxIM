package com.ifx.connect.task;

import com.ifx.connect.proto.Protocol;

public interface TaskLifeStyle {

    public void init();

    public void release();

    public void doTask(Protocol protocol);

}
