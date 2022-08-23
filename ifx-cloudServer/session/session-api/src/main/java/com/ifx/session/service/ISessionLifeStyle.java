package com.ifx.session.service;

public interface ISessionLifeStyle {


    public void initialize();

    public void start();

    public void add();

    public void hangOn();

    public void reConnect();

    public void release();




}
