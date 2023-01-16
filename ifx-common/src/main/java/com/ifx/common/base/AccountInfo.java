package com.ifx.common.base;

import com.ifx.common.acc.AccountSPI;
import lombok.Data;

import java.io.Serializable;

/***
 * 账户基本信息  公用模块
 */
@Data
public class AccountInfo implements Serializable, AccountSPI {
    private String userId;  // 用户id

    private String account;  // 账户

    private String userName; // 用户姓名

//    @Email(message = "非法的邮箱格式！")
    private String email;  // 邮箱

    @Override
    public String accountId() {
        return getAccount();
    }

    @Override
    public String getAccountName() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccount() {
        return account;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
