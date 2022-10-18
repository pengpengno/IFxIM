package com.ifx.session.vo;

import com.ifx.common.base.AccountInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionMsgVo implements Serializable {

    private String content;  // 消息文本

    private AccountInfo fromAccountInfo; //发送基本信息

    private Long sessionId;  // 会话

    private String createDate;  // 创建时间


}
