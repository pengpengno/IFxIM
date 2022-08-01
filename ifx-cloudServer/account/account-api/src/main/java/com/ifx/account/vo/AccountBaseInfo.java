package com.ifx.account.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class AccountBaseInfo implements Serializable {

    private String accountId;

    private String accountName;

    private String age;

    private String email;


}
