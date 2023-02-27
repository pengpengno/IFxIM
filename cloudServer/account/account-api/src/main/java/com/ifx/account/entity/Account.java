package com.ifx.account.entity;

import com.ifx.common.acc.AccountSPI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 基本用户信息表
 *
 * @TableName account
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("account")
public class Account extends BaseEntity implements Serializable , AccountSPI {


    /**
     * 账号
     */
    @Column("account")
    private String account;

    /**
     * 用户名称
     */
    @Column("user_name")
    private String userName;

    @Column(value = "user_nickname")
    private String userNickName;

    /**
     * 密码
     */
    @Column(value = "password")
    private String password;

    /**
     * 盐值
     */
    @Column("salt")
    private String salt;


    /**
     * 邮箱
     */
    @Column("email")
    private String email;

    /**
     * 居住地址
     */
    @Column("pwdhash")
    private String pwdhash;



    @Override
    public String accountId() {
        return account;
    }

    @Override
    public String accountName() {
        return userName;
    }


}
