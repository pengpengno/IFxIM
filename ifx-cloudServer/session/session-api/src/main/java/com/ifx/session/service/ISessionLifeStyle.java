package com.ifx.session.service;

import java.util.Set;

public interface ISessionLifeStyle {


    public Long initialize();

    public Long initialize(Set<String> accounts);

    public void start();

    public void hangOn();

    public Boolean reConnect();

    public Boolean release();




}
