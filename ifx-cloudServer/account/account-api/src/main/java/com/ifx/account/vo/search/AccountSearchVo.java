package com.ifx.account.vo.search;

import lombok.Data;

import java.io.Serializable;

/**
 * 账户搜索vo
 */
@Data
public class AccountSearchVo implements Serializable {

    private String account;

    private String likeAccount;

    private String name;

    private String likeName;

    private String nameChar; // 名称前缀

    private String mail; // 邮箱


}
