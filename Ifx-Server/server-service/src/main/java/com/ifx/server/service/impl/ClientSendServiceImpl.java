package com.ifx.server.service.impl;

import com.ifx.connect.proto.Protocol;
import com.ifx.server.netty.NettyS2CAction;
import com.ifx.server.netty.holder.NettyContext;
import com.ifx.server.service.ClientSendService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
public class ClientSendServiceImpl implements ClientSendService {
    @Autowired
    private NettyS2CAction  nettyS2CAction;

    @Override
    public void sendClient(String accountId, String msg) {
        Protocol<Object> protocol = new Protocol<>();
        protocol.setBody(msg);
        nettyS2CAction.sendProtoCol(accountId,protocol);
    }


    public void sendClient(String accountId, String formAccount,String msg) {
        Protocol<Object> protocol = new Protocol<>();
        protocol.setBody(msg);

        nettyS2CAction.sendProtoCol(accountId,protocol);
    }

    @Override
    public void sendClientBatch(String accountId, List<String> msgs) {

    }
}
