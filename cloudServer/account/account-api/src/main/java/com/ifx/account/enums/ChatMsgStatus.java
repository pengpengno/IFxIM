package com.ifx.account.enums;

import com.ifx.connect.proto.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息状态
 */
@Getter
@AllArgsConstructor
public enum ChatMsgStatus {

    READ (Chat.ChatMessage.MessagesStatus.READ) ,
    UNREAD (Chat.ChatMessage.MessagesStatus.UNREAD)  ,
    REJECT (Chat.ChatMessage.MessagesStatus.REJECT) ,
    SENT (Chat.ChatMessage.MessagesStatus.SENT) ,

    UNSENT  (Chat.ChatMessage.MessagesStatus.UNSENT) ,

    SENTFAIL (Chat.ChatMessage.MessagesStatus.SENTFAIL) ,

    HISTORY(Chat.ChatMessage.MessagesStatus.HISTORY)  ,
    ;


    private final Chat.ChatMessage.MessagesStatus status;



}
