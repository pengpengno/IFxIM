package com.ifx.server.service.impl;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import com.ifx.server.netty.NettyS2CAction;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@DubboService
public class ChatSendService {

    @Autowired
    private NettyS2CAction nettyS2CAction;

    public void sendChatService(String toAccountId ,String msg){
        Protocol protocol = new Protocol<>();
        protocol.setBody(msg);
        protocol.setType(IFxMsgProtocol.MSG_SENT_HEADER);
        nettyS2CAction.sendProtoCol(toAccountId,protocol);
    }
}
