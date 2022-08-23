package com.ifx.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.ifx.common.base.CurrentAccount;

public class AccountContext {
    private static final TransmittableThreadLocal<CurrentAccount> currentLocal = new TransmittableThreadLocal<>();



    public void rmAccount(){
        currentLocal.remove();
    }
    public void setCurAccount(CurrentAccount curAccount){
        currentLocal.set(curAccount);
    }
    public CurrentAccount getCurAccount(){
        return currentLocal.get();
    }


}
