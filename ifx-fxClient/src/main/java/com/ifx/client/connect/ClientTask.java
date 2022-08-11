package com.ifx.client.connect;

import com.ifx.connect.proto.Protocol;


public interface ClientTask {

    public void doBioWork(Protocol protocol);

    public void doNioWork(Protocol protocol);

    public Protocol doBioReq(Protocol protocol);

    public Protocol doNioReq(Protocol protocol);




}
