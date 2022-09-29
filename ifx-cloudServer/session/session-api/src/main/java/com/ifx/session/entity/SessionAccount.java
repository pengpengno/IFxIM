package com.ifx.session.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会话账户中间表
 * @TableName session_account
 */
@TableName(value ="session_account")
@Data
public class SessionAccount implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话
     */
    private Long session_id;

    /**
     * 会话账号
     */
    private String account;

    /**
     * 创建时间
     */
    private LocalDateTime create_time;

    /**
     * 修改时间
     */
    private LocalDateTime update_time;

    /**
     * 删除标志
     */
    private Integer active;

    /**
     * 版本
     */
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}