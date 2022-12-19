package com.ifx.connect.task.meta;

import com.ifx.connect.task.TaskLifeStyle;
import com.ifx.connect.task.handler.TaskHandler;
import lombok.Builder;
import lombok.Data;

/**
 * <br/>任务Task元数据
 * <p></p>
 */
//@Data
@Builder
public class TaskMeta  implements TaskLifeStyle {

    private TaskHandler taskHandler;

    private String taskName; // [optional]任务名称

    private String taskId;  // taskUuid

    private TaskMeta next; // 执行链

    private TaskState taskState;   // 执行状态

    @Override
    public void init() {

    }

    @Override
    public void release() {

    }

    @Override
    public TaskState getState() {
        return null;
    }


}
