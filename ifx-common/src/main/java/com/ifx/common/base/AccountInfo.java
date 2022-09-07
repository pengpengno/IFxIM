package com.ifx.common.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountInfo implements Serializable {

    private String userId;

    private String account;

    private String userName;

    private String email;

}
