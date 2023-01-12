package com.ifx.account.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
@Data
public class AccountRelationVo {

    private String account ;

    private Set<String>  relations;
}
