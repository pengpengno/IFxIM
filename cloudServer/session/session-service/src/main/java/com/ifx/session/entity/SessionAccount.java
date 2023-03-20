package com.ifx.session.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 会话账户中间表
 * @TableName session_account
 */
@Table(value ="session_account")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SessionAccount extends BaseEntity implements Serializable {


    /**
     * 会话
     */
    @Column(value = "session_id")
    private Long sessionId;

    /**
     * 用户id
     */
    @Column(value = "user_id")
    private Long userId;


}