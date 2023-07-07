package com.ifx.account.vo;

import com.ifx.common.base.AccountInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
public class ChatMsgVo implements Serializable {

    @NotNull(message = "消息存储失败！",groups = {ChatRecord.class})
    private Long msgId ;

    private String content;  // 消息文本

    /***
     * @see com.ifx.account.enums.ContentType
     */
    private String contentType;

    @NotNull(message = "发送人不可为空",groups = ChatPush.class)
    private AccountInfo fromAccount;

    @NotNull(message = "会话Id不可为空！",groups = ChatPush.class)
    private Long sessionId;  // 会话


    private String msgSendTime;  // 创建时间 客户端创建时间 不考虑时区


    //  yyyy-MM-dd HH:mm:ss
    private String msgCreateTime;  // 创建时间 客户端创建时间 不考虑时区



    /**
     * 消息推送
     * @author pengpeng
     * @description
     * @date 2023/1/16
     */
    public interface ChatPush {
    }

    public interface ChatRecord{

    }

}
