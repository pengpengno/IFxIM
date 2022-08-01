package com.ifx.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 基本用户信息表
 * @TableName account
 */
@TableName(value ="account")
@Data
public class Account implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账户密码
     */
    private String accountPsd;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 创建人编号
     */
    private String createAccount;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人编号
     */
    private String updateAccount;

    /**
     * 修改时间
     */
    private Date updateDate;

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