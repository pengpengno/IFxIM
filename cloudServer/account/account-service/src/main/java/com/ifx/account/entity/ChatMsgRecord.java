package com.ifx.account.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/31
 */
@EqualsAndHashCode(callSuper = true)
@Table("chat_msg_record")
@Data
public class ChatMsgRecord extends BaseEntity{

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
