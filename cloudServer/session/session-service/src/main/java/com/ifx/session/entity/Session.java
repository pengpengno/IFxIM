package com.ifx.session.entity;

import lombok.*;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session extends BaseEntity implements Serializable {


    /**
     * 会话名称（系统预定义）
     */
    @Column(value = "session_name")
    private String sessionName;


}