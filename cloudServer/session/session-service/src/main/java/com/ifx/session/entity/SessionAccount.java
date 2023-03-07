package com.ifx.session.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 会话账户中间表
 * @TableName session_account
 */
@Table(value ="session_account")
@Data
public class SessionAccount extends BaseEntity implements Serializable {


    /**
     * 会话
     */
    private Long sessionId;

    /**
     * 会话账号
     */
    private Long userId;


}