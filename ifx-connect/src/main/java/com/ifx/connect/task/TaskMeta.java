package com.ifx.connect.task;

import lombok.Data;

/**
 * 任务Task元数据
 */
@Data
public class TaskMeta {

    private TaskHandler taskHandler;

    private String taskName;

    private String taskId;


}
