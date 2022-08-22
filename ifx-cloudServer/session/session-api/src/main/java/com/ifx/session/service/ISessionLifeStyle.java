package com.ifx.session.service;

public interface ISessionLifeStyle {


    public void initialize();

    public void start();

    public void stop();

    public void reConnect();


}
