package com.ifx.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ifx.common.acc.AccountSPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本用户信息表
 *
 * @TableName account
 */
@TableName(value = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable , AccountSPI {



    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_nickname")
    private String userNickName;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 盐值
     */
    @TableField("salt")
    private String salt;

//    /**
//     * 出生日期
//     */
//    @TableField("birthday")
//    private Date birthday;


    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 居住地址
     */
    @TableField("pwdhash")
    private String pwdhash;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date create_time;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date update_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    @Override
    public String accountId() {
        return account;
    }

    @Override
    public String getAccountName() {
        return userName;
    }
}
