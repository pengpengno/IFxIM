package com.ifx.client.connect;

import com.ifx.connect.proto.Protocol;

public class AbstractTask implements ClientTask{


    private String taskCode;
    private Protocol protocol;

    /**<K,V> k */



    @Override
    public void doBioWork(Protocol protocol) {

    }

    @Override
    public void doNioWork(Protocol protocol) {

    }

    @Override
    public Protocol doBioReq(Protocol protocol) {
        return null;
    }

    @Override
    public Protocol doNioReq(Protocol protocol) {
        return null;
    }
}
