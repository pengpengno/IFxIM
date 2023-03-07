package com.ifx.account.vo;

import com.ifx.account.validator.ACCOUNTLOGIN;
import com.ifx.common.acc.AccountSPI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/***
 * 用户基础信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountVo implements Serializable , AccountSPI {

    private Long userId;
    @NotNull(message = "账户不可为空！",groups = ACCOUNTLOGIN.class)
    private String account;

    private String userName;   // 用户姓名
    @NotNull(message = "密码不可为空！",groups = ACCOUNTLOGIN.class)
    private String password;

    private String email;  // 电子邮件

    @Override
    public String accountId() {
        return account;
    }

    @Override
    public String accountName() {
        return userName;
    }
}
