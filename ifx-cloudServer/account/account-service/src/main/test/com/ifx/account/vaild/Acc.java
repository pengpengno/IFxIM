package com.ifx.account.vaild;

import com.ifx.common.base.AccountInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
    @NotNull
    private String testString;
}
