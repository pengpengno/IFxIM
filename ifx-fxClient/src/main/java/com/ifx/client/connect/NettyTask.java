package com.ifx.client.connect;

import com.ifx.client.connect.netty.NettyClient;
import com.ifx.connect.proto.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component(value = "nettyTask")
public class NettyTask extends AbstractTask{


    @Autowired
    private NettyClient nettyClient;


    @Override
    public void doBioWork(Protocol protocol) {
//        super.doBioWork(protocol);
        nettyClient.write()
    }

    @Override
    public void doNioWork(Protocol protocol) {
        super.doNioWork(protocol);
    }

    @Override
    public Protocol doBioReq(Protocol protocol) {

    }

    @Override
    public Protocol doNioReq(Protocol protocol) {
        return super.doNioReq(protocol);
    }
}
