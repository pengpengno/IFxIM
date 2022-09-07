package com.ifx.session.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ChatMsgVo implements Serializable {

    private String content;  // 消息文本

    private String fromAccount;  //  发送者

    private Long toSession;  // 会话

    private String createDate;  // 创建时间


}
