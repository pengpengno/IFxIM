package com.ifx.common.ex.valid;

import com.ifx.common.BaseException;
import com.ifx.common.errorMsg.IErrorMsg;

public class ValidationException extends BaseException {

    public ValidationException(IErrorMsg errorMsg) {
        super(errorMsg);
    }

    public ValidationException(IErrorMsg errorMsg, String detailMsg) {
        super(errorMsg, detailMsg);
    }

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    protected ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
