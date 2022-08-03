package com.ifx.account.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class AccountBaseInfo implements Serializable {

    private String userId;

    private String account;

    private String userName;

    private String password;

    private String email;

}
