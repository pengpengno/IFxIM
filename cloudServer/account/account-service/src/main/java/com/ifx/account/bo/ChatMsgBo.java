package com.ifx.account.bo;

import com.ifx.account.enums.ContentType;
import com.ifx.account.service.chat.ReadAbleMsg;
import com.ifx.common.base.AccountInfo;
import lombok.Data;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/29
 */
@Data
public class ChatMsgBo implements Serializable , ReadAbleMsg {

    private Long msgId ;

    private Long sessionId;

    private AccountInfo fromAccountInfo ;

    private AccountInfo toAccountInfo ;

    private String content ;

    private ContentType contentType;

    @Override
    public String read() {
        return getContent();
    }

    @Override
    public void write(byte[] bytes) {
        String content = new String(bytes, Charset.defaultCharset());
        write(content);
    }

    @Override
    public void write(String content) {
        setContent(content);
    }
}
