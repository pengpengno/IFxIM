package com.ifx.common.ex.bus.acc;

import com.ifx.common.ex.bus.IFXException;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
public class AccountException extends IFXException {


    public AccountException() {
        super();
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

    protected AccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
