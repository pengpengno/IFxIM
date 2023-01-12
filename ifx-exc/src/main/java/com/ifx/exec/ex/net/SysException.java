package com.ifx.exec.ex.net;

import com.ifx.exec.BaseException;
import com.ifx.exec.ExceptionMessage;

/**
 * 系统异常
 * @author pengpeng
 * @date 2022/12/8
 */
public class SysException extends BaseException {

    public SysException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public SysException(ExceptionMessage exceptionMessage, String detailMsg) {
        super(exceptionMessage, detailMsg);
    }

    public SysException() {
        super();
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }

    protected SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
