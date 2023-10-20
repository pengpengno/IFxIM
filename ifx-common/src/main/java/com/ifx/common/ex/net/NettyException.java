package com.ifx.common.ex.net;

import com.ifx.common.errorMsg.IErrorMsg;

/***
 * Netty 异常信息
 */
public class NettyException extends NetException {

    public NettyException(IErrorMsg errorMsg, String detailMsg) {
        super(errorMsg, detailMsg);
    }

    public NettyException(IErrorMsg iErrorMsg) {
        super(iErrorMsg);
    }


    public NettyException() {
        super();
    }

    public NettyException(String message) {
        super(message);
    }

    public NettyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NettyException(Throwable cause) {
        super(cause);
    }

    protected NettyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
