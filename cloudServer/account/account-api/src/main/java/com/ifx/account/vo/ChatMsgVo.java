package com.ifx.account.vo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ifx.common.base.AccountInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Comparator;

@Data
public class ChatMsgVo implements Serializable , Comparator<ChatMsgVo> {

    @NotNull(message = "消息存储失败！",groups = {ChatRecord.class})
    private Long msgId ;

    private Long msgOrderTag ;

    private String content;  // 消息文本

    /***
     * @see com.ifx.account.enums.ContentType
     */
    private String contentType;

    @NotNull(message = "发送人不可为空",groups = ChatPush.class)
    private AccountInfo fromAccount;

    @NotNull(message = "会话Id不可为空！",groups = ChatPush.class)
    private Long sessionId;  // 会话


    private String msgSendTime;  // 创建时间 客户端创建时间 不考虑时区 yyyy-MM-dd HH:mm:ss


    //  yyyy-MM-dd HH:mm:ss
    private String msgCreateTime;  // 创建时间 服务端创建时间 不考虑时区



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



    @Override
    public int compare(ChatMsgVo o1, ChatMsgVo o2) {

        if (o1 == null && o2 == null){
            return 0;
        }
        if (o1 == null){
            return 1;
        }

        String msgCreateTime1 = o1.getMsgCreateTime();
        String msgCreateTime2 = o2.getMsgCreateTime();
        return DateUtil.compare(DateTime.of(msgCreateTime1, DatePattern.NORM_DATETIME_PATTERN), DateTime.of(msgCreateTime2, DatePattern.NORM_DATETIME_PATTERN));
    }
}
