package com.ifx.common.ex.net;

import com.ifx.common.errorMsg.IErrorMsg;

/**
 * 协议异常
 */
public class ProtocolException extends NetException {

    public ProtocolException(IErrorMsg errorMsg) {
        super(errorMsg);
    }

    public ProtocolException(IErrorMsg errorMsg, String detailMsg) {
        super(errorMsg, detailMsg);
    }

    public ProtocolException() {
        super();
    }

    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolException(Throwable cause) {
        super(cause);
    }

    protected ProtocolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
