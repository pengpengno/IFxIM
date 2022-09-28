package com.ifx.session.service;

import com.ifx.common.base.AccountInfo;

import java.util.Set;

public interface ISessionAction {


    public void add();

    public void addAcc(Long sessionId, Set<AccountInfo> accountInfo);

    public Long addAcc(Set<String> account);


    public void flush2Db();

    public void notifyAllAcc();


}
