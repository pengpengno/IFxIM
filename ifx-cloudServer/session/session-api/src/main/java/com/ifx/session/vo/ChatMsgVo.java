package com.ifx.session.vo;

import com.ifx.common.base.AccountInfo;
import lombok.Data;

import java.io.Serializable;
@Data
public class ChatMsgVo implements Serializable {

    private String content;  // 消息文本

    private String fromAccount;  //  发送者

    private AccountInfo formAccount; //发送基本信息

    private Long sessionId;  // 会话

    private String createDate;  // 创建时间


}
