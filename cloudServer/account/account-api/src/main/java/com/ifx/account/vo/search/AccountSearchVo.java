package com.ifx.account.vo.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 账户搜索vo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountSearchVo implements Serializable {

    private String account;

    private String likeAccount;

    private String likeName; //模糊搜索姓名

    private String nameCharPreFix; // 名称前缀

    private String mail; // 邮箱


}
