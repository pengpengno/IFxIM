package com.ifx.common.res;

public interface ResAction<T> {


     T getRes();

    void suc();

    void fail();


    void addData(T data);


}
