package com.ifx.connect.task;

import com.ifx.connect.task.meta.TaskMeta;
import com.ifx.connect.task.meta.TaskState;

/**
 * Task生命周期
 *
 */
public interface TaskLifeStyle{
    /**
     * 初始化任务{@link TaskMeta}
     */
    public void init();

    /**
     * 释放任务资源{@link TaskMeta}
     */
    public void release();

    /**
     * 获取 任务 {@link TaskMeta} 执行状态
     *
     */
    public TaskState getState();


}
