package com.ifx.session.entity;


import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 消息表
 * @TableName chat_msg
 */
@Table("chat_msg")
@Data
public class ChatMsg extends BaseEntity implements Serializable {

    /**
     * 发送者
     */
    @Column
    private String fromAccount;

    /**
     * 接受的会话Id
     */
    @Column
    private String sessionId;

    /**
     * 消息文本
     */
    @Column
    private String content;
    /***
     * @see com.ifx.session.enums.ContentType
     * @description  消息文本类型
     */
    @Column
    private String contentType ;


    /***
     * @see com.ifx.session.enums.ChatMsgStatus
     */
    @Column
    private String status ;

}