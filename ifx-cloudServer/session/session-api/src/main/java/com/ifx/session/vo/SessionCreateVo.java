package com.ifx.session.vo;

import com.ifx.account.entity.Account;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class SessionCreateVo implements Serializable {

    private String sessionTitle;

    private String sessionDetail;

    private Set<Account> accounts;


}
