package com.ifx.connect.task.meta;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务执行状态实体
 * @author pengpeng
 * @date 2022/12/9
 */
public class TaskState implements Serializable {


    public String taskState; // 任务执行状态

    public String taskMsg; //执行信息  // optional

    public Date taskCreateTime;

    public Date taskCompletedTime;


}
