package com.ifx.common.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrentAccount implements Serializable {

    private String account;

    private String accountNickName;

}
