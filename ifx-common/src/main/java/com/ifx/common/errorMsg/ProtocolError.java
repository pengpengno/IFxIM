package com.ifx.common.errorMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 协议异常处理
 */
@Getter
@AllArgsConstructor
public enum ProtocolError implements IErrorMsg {

    PROTOCOL_NOT_SUPPORT(2000001,"协议不支持！"),
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
