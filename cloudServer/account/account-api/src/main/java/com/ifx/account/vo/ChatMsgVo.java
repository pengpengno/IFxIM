package com.ifx.account.vo;

import com.ifx.account.valiator.ChatPush;
import com.ifx.common.base.AccountInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
public class ChatMsgVo implements Serializable {

    private String content;  // 消息文本


    private String contentType;


    @NotNull(message = "发送人不可为空",groups = ChatPush.class)
    private AccountInfo fromAccount;

    @NotNull(message = "会话Id不可为空！")
    private Long sessionId;  // 会话


    private String msgSendTime;  // 创建时间 客户端创建时间 不考虑时区


}
