package com.ifx.account.controller;

import com.ifx.common.base.AccountInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/20
 */

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acc extends AccountInfo {
    @NotNull(message = "testString not null")
    private String testString;

    private String s;
}
