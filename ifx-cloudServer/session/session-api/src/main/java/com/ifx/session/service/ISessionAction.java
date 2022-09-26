package com.ifx.session.service;

import com.ifx.common.base.AccountInfo;

public interface ISessionAction {


    public void add();

    public void addAcc(Long sessionId, AccountInfo accountInfo);

    public void flush2Db();

    public void notifyAllAcc();


}
