package com.ifx.account.vo.chat;

import com.ifx.account.enums.ChatMsgStatus;
import com.ifx.account.enums.ContentType;
import com.ifx.account.service.chat.ReadAbleMsg;
import com.ifx.common.base.AccountInfo;
import lombok.Data;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
@Data
public class ChatMsgRecordVo implements ReadAbleMsg {

    private Long msgId;

    private String content;

    private ContentType contentType;


    /***
     * @see com.ifx.account.enums.ChatMsgStatus
     */
    private ChatMsgStatus status;


    private AccountInfo fromAccount;


    private AccountInfo toAccount;

    private Long sessionId;




    @Override
    public String read() {
        return content;
    }

    @Override
    public void write(byte[] bytes) {
        content = new String(bytes);
    }

    @Override
    public void write(String content) {
        this.content = content;
    }
}
