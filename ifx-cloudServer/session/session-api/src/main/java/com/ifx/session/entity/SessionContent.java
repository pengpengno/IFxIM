package com.ifx.session.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会话信息表
 * @TableName session_content
 */
@TableName(value ="session_content")
@Data
public class SessionContent implements Serializable {
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
     * 会话文本
     */
    private String session_content;

    /**
     * 创建会话账号
     */
    private String create_account;

    /**
     * 更新会话账号
     */
    private String update_account;

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