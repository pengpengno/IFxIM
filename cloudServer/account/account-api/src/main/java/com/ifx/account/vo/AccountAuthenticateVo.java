package com.ifx.account.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountAuthenticateVo {

    private String jwt;
}
