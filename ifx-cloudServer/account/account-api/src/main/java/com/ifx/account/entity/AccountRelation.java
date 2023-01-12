package com.ifx.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 账户关系表
 * @TableName account_relation
 */
@TableName(value ="account_relation")
@Data
public class AccountRelation implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private String account;

    /**
     * 账号关系集合
     */
    private String account_relations;

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