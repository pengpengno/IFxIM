package com.ifx.server.service.impl;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import com.ifx.server.netty.NettyS2CAction;
import com.ifx.server.service.Server2ClientService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@DubboService
@Service
public class Server2ClientServiceImpl implements Server2ClientService {
    @Autowired
    private NettyS2CAction  nettyS2CAction;

    @Override
    public void sendClient(String accountId, String msg) {
        Protocol protocol = new Protocol();
        protocol.setProtocolBody(msg);
        protocol.setType(IFxMsgProtocol.SERVER_TO_CLIENT_MSG_HEADER);
        nettyS2CAction.sendProtoCol(accountId,protocol);
    }


    public void sendClient(String toAccountId, String formAccount,String msg) {
        Protocol protocol = new Protocol();
        protocol.setProtocolBody(msg);
        protocol.setType(IFxMsgProtocol.SERVER_TO_CLIENT_MSG_HEADER);
        nettyS2CAction.sendProtoCol(toAccountId,protocol);
    }

    @Override
    public void sendClientBatch(String accountId, List<String> msgs) {
        // TODO   批量推送消息 避免每次单条消息都进行推送的高频
    }
}
