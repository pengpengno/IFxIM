package com.ifx.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 基本用户信息表
 *
 * @TableName account
 */
@TableName(value = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

    public static Account instance = getInstance();

    public static Account getInstance() {
        Date now = new Date();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return new Account(null, uuid, null, null, null, null, null, now, null, null, now, now);
    }



    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;


    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 昵称
     */
    @TableField("user_nickname")
    private String user_nickname;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 盐值
     */
    @TableField("salt")
    private String salt;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private Date birthday;


    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 居住地址
     */
    @TableField("address")
    private String address;

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
}
