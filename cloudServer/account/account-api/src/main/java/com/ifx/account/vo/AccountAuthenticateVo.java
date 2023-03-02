package com.ifx.account.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/2
 */
@Data
@Builder
public class AccountAuthenticateVo {

    private String jwt;
}
