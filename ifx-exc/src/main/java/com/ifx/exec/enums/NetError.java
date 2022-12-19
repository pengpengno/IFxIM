package com.ifx.exec.enums;

import com.ifx.exec.constant.ExceptionConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengpeng
 * @date 2022/12/8
 */
@Getter
@AllArgsConstructor
public enum NetError implements ExceptionConstant {


    LOCAL_NETWORK_IS_VAILD(1000001,"本地网络异常！"),
    ;

    private final Integer code ;

    private final String message;


    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
