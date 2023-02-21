package com.ifx.exec.errorMsg.acc;

import com.ifx.exec.errorMsg.IErrorMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
@Getter
@AllArgsConstructor
public enum AccErrorMsg implements IErrorMsg {

    LOGIN_PSD_INVAILD(100001,"密码错误！"),
    ACCOUNT_EXISTS(100002 ,"账户已存在！")

    ;

    private final Integer code ;

    private final String message;


    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public Integer getErrorCode() {
        return code;
    }
}
