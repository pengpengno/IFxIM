package com.ifx.account.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthVo implements Serializable {


    private String  tag ;


    private String publicKey;  // 公钥串


}
