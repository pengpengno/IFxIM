package com.ifx.common.errorMsg.connect;

import com.ifx.common.errorMsg.IErrorMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/6
 */
@AllArgsConstructor
@Getter
public enum ConnectErrorMsg implements IErrorMsg {


    NOT_FOUND_CONNECTION(100001 , "未搜索到账户客户端长链接！")
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
