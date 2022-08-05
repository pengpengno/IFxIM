package com.ifx.connect.connection.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * V1 版本指令集
 */
public enum ConnectCommandEnum {
    LOGIN("登录"),
    REGISTER("注册"),
    FORGET("忘记密码"),

    AUTO_RECONNECT("自动重连"),
    PULL_RELATION("拉取好友关系"),
    PULL_OFFLINE_MSG("拉取离线消息"),
    SEND_MSG("消息发送");
    ;

    private final String desc;

//    private final String service; //  服务模块

}
