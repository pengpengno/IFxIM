package com.ifx.connect.task.handler.def;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的 TaskHandler
 * @author pengpeng
 * @date 2022/12/9
 */
@Slf4j
public class DefaultHandler {
    /**
     * 默认 Handler   心跳log
     */
    public static class HeartBeatHandler implements TaskHandler{
        @Override
        public void accept(Protocol protocol) {
            log.info("心跳包执行完毕! 当前时间为 {}");
        }
    }
    public static TaskHandler HEART_BEAT_HANDLER = (Task) -> {log.info("执行心跳包！");};  // 默认的心跳 Handler

}
