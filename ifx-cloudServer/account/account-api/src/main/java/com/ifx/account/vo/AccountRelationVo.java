package com.ifx.account.vo;

import com.ifx.account.validat.ACCOUNTLOGIN;
import com.ifx.account.validat.ACCOUTRELATIONINSERT;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 *
 * 账户关系图vo
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
@Data
public class AccountRelationVo {

    @NotBlank(message = "账户不可为空！",groups = ACCOUNTLOGIN.class)
    private String account ;
    @NotEmpty(message = "添加关系不可为空!",groups = ACCOUTRELATIONINSERT.class)
    private Set<String>  relations;
}
