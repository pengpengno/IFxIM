package com.ifx.common.errorMsg;

/**
 * 异常信息接口处理
 * @description  异常信息 接口 提供了对 异常信息和异常状态玛的抽象
 * @author pengpeng
 * @date 2023/1/9
 */
public interface IErrorMsg {


    public String getErrorMessage();


    public Integer getErrorCode();


}
