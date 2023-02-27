package com.ifx.session.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 会话表
 * @TableName session
 */
@EqualsAndHashCode(callSuper = true)
@Table(value ="session")
@Data
public class Session extends BaseEntity implements Serializable {

    /**
     * 会话标识
     */
    @Column
    private Long sessionId;

    /**
     * 会话名称（系统预定义）
     */
    @Column
    private String sessionName;

    /**
     * 会话名称（应用层自定义）
     */
    @Column
    private String sessionType;

    @Column
    private String sessionGroup; // 会话组

}