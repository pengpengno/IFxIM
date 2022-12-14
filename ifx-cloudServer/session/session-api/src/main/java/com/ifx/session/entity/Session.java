package com.ifx.session.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会话表
 * @TableName session
 */
@TableName(value ="session")
@Data
public class Session implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 会话标识
     */
    private Long session_id;

    /**
     * 会话名称（系统预定义）
     */
    private String session_name;

    /**
     * 会话名称（应用层自定义）
     */
    private String session_nick_name;

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