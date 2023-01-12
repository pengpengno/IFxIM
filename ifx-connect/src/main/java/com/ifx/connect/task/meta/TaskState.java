package com.ifx.connect.task.meta;

import java.io.Serializable;

/**
 * 任务执行状态
 * @author pengpeng
 * @date 2022/12/9
 */
public class TaskState implements Serializable {


    public Boolean taskState; // 任务执行状态

    public String taskMsg; //执行信息 // optional
}
