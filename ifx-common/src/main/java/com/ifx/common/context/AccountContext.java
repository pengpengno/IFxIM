package com.ifx.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.ifx.common.base.AccountInfo;

/**
 * 用户信息上下文
 */
public class AccountContext {
    private static final TransmittableThreadLocal<AccountInfo> currentLocal = new TransmittableThreadLocal<>();

    public static void rmAccount(){
        currentLocal.remove();
    }
    public static void setCurAccount(AccountInfo curAccount){
        currentLocal.set(curAccount);
    }
    public static AccountInfo getCurAccount(){
        return currentLocal.get();
    }


}
