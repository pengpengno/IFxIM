package com.ifx.account.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */

@Data
@Table(value = "chat_msg_record")
public class ChatMsgRe extends BaseEntity implements Serializable {

    @Column("session_id")
    private Long sessionId;

    @Column("msg_id")
    private Long msgId;

    @Column("to_user_id")
    private Long toUserId;

    /***
     * {@link com.ifx.session.enums.ChatMsgStatus [消息状态] }
     */
    @Column("status")
    private String status ;


}
