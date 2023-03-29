package com.ifx.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
public class Session extends BaseEntity implements Serializable {


    /**
     * 会话名称（系统预定义）
     */
    @Column(value = "session_name")
    private String sessionName;


}