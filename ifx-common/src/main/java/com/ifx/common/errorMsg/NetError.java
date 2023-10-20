package com.ifx.common.errorMsg;

import com.ifx.common.constant.ExceptionConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 网络异常
 * @author pengpeng
 * @date 2022/12/8
 */
@Getter
@AllArgsConstructor
public enum NetError implements ExceptionConstant ,
    IErrorMsg
{


    LOCAL_NETWORK_IS_VAILD(1000001,"本地网络异常！"),

    REMOTE_NET_CAN_NOT_CONNECT(1000002,"远程服务器无法访问!");
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



