package com.ifx.connect.task.meta;

import com.ifx.connect.task.TaskLifeStyle;
import com.ifx.connect.task.handler.TaskHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>任务Task元数据
 * <p></p>
 */
//@Data
@Builder
@Slf4j
public class TaskMeta  implements TaskLifeStyle {

    private TaskHandler taskHandler;

    private String taskName; // [optional]任务名称

    private String taskId;  // taskUuid

    private TaskState taskState;   // 执行状态

    @Override
    public void init() {
        log.debug("初始化任务 \r");
    }
    public void release(){
        taskHandler = (protocol)-> log.info("该任务已被释放");
    }


}
