package com.ifx.exec;

import java.util.logging.ErrorManager;

/**
 * 基础系统异常
 * {@link ExceptionMessage} 异常消息接口主要用于标识异常的类型
 * detailMsg 用于标识异常的详细信息
 * 例子
 */
public class BaseException extends RuntimeException{

    private ExceptionMessage exceptionMessage;

    private String detailMsg;


    public BaseException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public BaseException(ExceptionMessage exceptionMessage,String detailMsg) {
        this.exceptionMessage = exceptionMessage;
        this.detailMsg = detailMsg;
    }


    public BaseException() {
        super();
    }


    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}