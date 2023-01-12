package com.ifx.exec.ex.net;

import com.ifx.exec.BaseException;
import com.ifx.exec.ExceptionMessage;
import com.ifx.exec.errorMsg.IErrorMsg;

/**
 * 网络异常
 * @author pengpeng
 * @date 2022/12/8
 */
public class NetException extends BaseException {

    public NetException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public NetException(IErrorMsg errorMsg) {
        super(errorMsg);
    }

    public NetException(IErrorMsg errorMsg, String detailMsg) {
        super(errorMsg, detailMsg);
    }

    public NetException(ExceptionMessage exceptionMessage, String detailMsg) {
        super(exceptionMessage, detailMsg);
    }

    public NetException() {
        super();
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetException(Throwable cause) {
        super(cause);
    }

    protected NetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
