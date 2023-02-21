package com.ifx.common.acc;

/**
 * 账户模块拓展点
 * @author pengpeng
 * @description
 * @date 2023/1/11
 */
public interface AccountSPI {

    /***
     * 获取用户身份唯一标识
     * @return
     */
    public String accountId() ;


    public String accountName();




}
