package com.ifx.client.service;

import com.ifx.common.base.AccountInfo;
import com.ifx.connect.proto.Protocol;
import com.ifx.session.service.ISessionAction;

import java.util.Set;

public class SessionActionService implements ISessionAction {


    @Override
    public void add() {

    }

    @Override
    public void addAcc(Long sessionId, Set<AccountInfo> accountInfo) {

    }

    @Override
    public Long addAcc(Set<String> account) {
        return null;
    }

    @Override
    public void flush2Db() {

    }

    @Override
    public void notifyAllAcc() {

    }


    public Protocol add0() {
        return null;

    }

    public Protocol addAcc0(Long sessionId, Set<AccountInfo> accountInfo) {
        return null;

    }

    public Protocol addAcc0(Set<String> account) {
        return null;
    }

    public Protocol flush2Db0() {
        return null;

    }

    public Protocol notifyAllAcc0() {
        return null;
    }
}
