package com.ifx.session.service;

public interface IChatAction {


    public void pushMsg(String fromAccount,String session,String msg);


    public void pullMsg(String fromAccount,String session);


    public void pullHisMsg(String session);





}
