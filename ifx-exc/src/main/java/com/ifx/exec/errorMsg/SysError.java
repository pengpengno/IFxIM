package com.ifx.exec.errorMsg;

import com.ifx.exec.constant.ExceptionConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统异常
 * @author pengpeng
 * @date 2022/12/8
 */
@Getter
@AllArgsConstructor
public enum SysError implements ExceptionConstant ,
    IErrorMsg
{


    NETTY_MOUDLE_IS_VALID(1000001,"网络模块尚未加载！"),
    ;

    private final Integer code ;

    private final String message;


    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


    @Override
    public String getErrorMessage() {
        return getMessage();
    }

    @Override
    public Integer getErrorCode() {
        return getCode();
    }
}



