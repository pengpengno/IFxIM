package com.ifx.common.ex.net;

import com.ifx.common.BaseException;
import com.ifx.common.errorMsg.IErrorMsg;

/**
 * 系统异常
 * @author pengpeng
 * @date 2022/12/8
 */
public class SysException extends BaseException {

    public SysException(IErrorMsg errorMsg) {
        super(errorMsg);
    }

    public SysException(IErrorMsg errorMsg, String detailMsg) {
        super(errorMsg, detailMsg);
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
