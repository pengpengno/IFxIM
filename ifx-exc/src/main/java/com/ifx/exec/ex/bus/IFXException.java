package com.ifx.exec.ex.bus;

import com.ifx.exec.BaseException;
import com.ifx.exec.errorMsg.IErrorMsg;

/**
 * @author pengpeng
 * @date 2022/12/8
 */
public class IFXException extends BaseException {

    private IErrorMsg errorMsg;

    private String detailMsg;

    public IFXException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public IFXException(ExceptionMessage exceptionMessage, String detailMsg) {
        super(exceptionMessage, detailMsg);
    }

    public IFXException() {
        super();
    }

    public IFXException(String message) {
        super(message);
    }

    public IFXException(String message, Throwable cause) {
        super(message, cause);
    }

    public IFXException(Throwable cause) {
        super(cause);
    }

    protected IFXException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
