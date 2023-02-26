package com.ifx.session.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 会话信息表
 * @TableName session_chat
 */
@Table(value ="session_chat")
@Data
public class SessionChat extends BaseEntity implements Serializable {

    /**
     * 会话
     */
    private Long session_id;

    /**
     * 会话文本
     */
    private String chat_content;

    /**
     * 接受账户
     */
    private Long to_account;

    /**
     * 消息类型
     */
    private String content_type;

    /**
     * 消息回执状态
     */
    private String call_back_status;


}