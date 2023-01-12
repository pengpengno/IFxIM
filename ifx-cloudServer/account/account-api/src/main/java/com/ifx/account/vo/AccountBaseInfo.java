package com.ifx.account.vo;

import com.ifx.account.validat.ACCOUNTLOGIN;
import com.ifx.common.acc.AccountSPI;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/***
 * 用户基础信息
 */
@Data
public class AccountBaseInfo implements Serializable , AccountSPI {

    private String userId;
    @NotNull(message = "账户不可为空！",groups = ACCOUNTLOGIN.class)

    private String account;

    private String userName;
    @NotNull(message = "密码不可为空！",groups = ACCOUNTLOGIN.class)
    private String password;

    private String email;

    @Override
    public String accountId() {
        return account;
    }
}
