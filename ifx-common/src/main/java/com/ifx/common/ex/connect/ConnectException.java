package com.ifx.common.ex.connect;

import com.ifx.common.BaseException;
import com.ifx.common.errorMsg.IErrorMsg;

/**
 * connection 异常
 * @author pengpeng
 * @description
 * @date 2023/3/6
 */
public class ConnectException extends BaseException {



    public ConnectException(IErrorMsg errorMsg) {
        super(errorMsg);
    }

    public ConnectException(IErrorMsg errorMsg, String detailMsg) {
        super(errorMsg, detailMsg);
    }

    public ConnectException() {
        super();
    }

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectException(Throwable cause) {
        super(cause);
    }

    protected ConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
