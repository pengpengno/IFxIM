package com.ifx.session.service;

import com.ifx.common.base.AccountInfo;

public interface ISessionLifeStyle {


    public void initialize();

    public void start();

    public void hangOn();

    public void reConnect();

    public void release();




}
