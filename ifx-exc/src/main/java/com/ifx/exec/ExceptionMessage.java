package com.ifx.exec;

import com.ifx.exec.constant.ExceptionConstant;

/**
 * exception errorMsg
 * apply
 */
public interface ExceptionMessage {

    String getMessage();

    Integer getCode();

    /**
     * 返回异常根标识
     * @return 异常根标识
     */
    default public String getRootCause(){
        return ExceptionConstant.DEFAULT_ROOT;
    }

}
