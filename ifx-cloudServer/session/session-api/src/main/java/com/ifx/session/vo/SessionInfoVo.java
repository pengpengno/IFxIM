package com.ifx.session.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;

/***
 * 会话基础信息
 */
@Data
public class SessionInfoVo implements Serializable {


    private ConcurrentSkipListSet<String> account;

    private String sessionId; // 会话标识

    private String name;  // 姓名


}
