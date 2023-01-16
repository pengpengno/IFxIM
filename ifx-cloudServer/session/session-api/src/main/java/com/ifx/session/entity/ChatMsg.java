package com.ifx.session.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息表
 * @TableName chat_msg
 */
@TableName(value ="chat_msg")
@Data
public class ChatMsg implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 发送者
     */
    private String fromAccount;

    /**
     * 接受的会话Id
     */
    private String toSessionId;

    /**
     * 消息文本
     */
    private String content;
    /***
     * @see
     * @description  消息文本类型
     */
    private String content_type ;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

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