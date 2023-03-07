package com.ifx.exec.ex.bus.session;

import com.ifx.exec.errorMsg.IErrorMsg;
import com.ifx.exec.ex.bus.IFXException;

/**
 * @author pengpeng
 * @date 2022/12/9
 */
public class SessionException extends IFXException {


    public SessionException(IErrorMsg errorMsg) {
        super(errorMsg);
    }

    public SessionException(IErrorMsg errorMsg, String detailMsg) {
        super(errorMsg, detailMsg);
    }

    public SessionException() {
        super();
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
