package com.ifx.exec;

import com.ifx.exec.errorMsg.IErrorMsg;

/**
 * 基础系统异常
 * {@link IErrorMsg} 异常消息接口主要用于标识异常的类型
 * detailMsg 用于标识异常的详细信息
 * 例子
 */
public class BaseException extends RuntimeException{


    private String detailMsg;  //详细信息

    private IErrorMsg errorMsg;  // 异常接口

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public IErrorMsg getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(IErrorMsg errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BaseException(IErrorMsg errorMsg){
        super(errorMsg.getErrorMessage());
        this.errorMsg = errorMsg;
    }
    public BaseException(IErrorMsg errorMsg,String detailMsg){
        this.errorMsg = errorMsg;
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
