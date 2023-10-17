package com.ifx.account.vo;

import com.ifx.account.validator.ACCOUNTLOGIN;
import com.ifx.account.validator.ACCOUTRELATIONINSERT;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 *
 * 账户关系图vo
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
@Data
@Builder
public class AccountRelationVo {

    @NotBlank(message = "账户不可为空！",groups = ACCOUNTLOGIN.class)
    private String account ;

    private Long relationId; // 账户关系标识
    @NotEmpty(message = "添加关系不可为空!",groups = ACCOUTRELATIONINSERT.class)
    private Set<String> friendAccountIds;



}
