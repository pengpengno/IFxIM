package com.ifx.session.vo;

import com.ifx.common.base.AccountInfo;
import com.ifx.session.valiator.ChatPush;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
public class ChatMsgVo implements Serializable {

    private String content;  // 消息文本
    @NotNull(message = "发送人不可为空",groups = ChatPush.class)
    private AccountInfo formAccount;
    @NotNull(message = "会话Id不可为空！")
    private Long sessionId;  // 会话

    private String msgSendTime;  // 创建时间


}
