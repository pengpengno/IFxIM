package com.ifx.exec.ex.bus.session;

import com.ifx.exec.ExceptionMessage;
import com.ifx.exec.ex.bus.IFXException;

/**
 * @author pengpeng
 * @date 2022/12/9
 */
public class SessionException extends IFXException {
    public SessionException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);

    }

    public SessionException(ExceptionMessage exceptionMessage, String detailMsg) {
        super(exceptionMessage, detailMsg);
    }


    public SessionException(String message) {
        super(message);
    }

    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionException(Throwable cause) {
        super(cause);
    }

    protected SessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}