package com.ifx.exec.enums;

import com.ifx.exec.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 协议异常处理
 */
@Getter
@AllArgsConstructor
public enum ProtocolError implements ExceptionMessage {

    PROTOCOL_NOT_SUPPORT(2000001,"协议不支持"),
    ;

    private final Integer code ;

    private final String message;


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
