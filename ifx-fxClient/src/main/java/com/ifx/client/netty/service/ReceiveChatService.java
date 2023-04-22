package com.ifx.client.netty.service;

import com.google.protobuf.Message;
import com.ifx.connect.enums.ProtocolMessageMapEnum;
import com.ifx.connect.spi.netty.ProtoBufProcess;
import reactor.netty.Connection;

/**
 * @author pengpeng
 * @description
 * @date 2023/4/22
 */
public class ReceiveChatService implements ProtoBufProcess {

    @Override
    public ProtocolMessageMapEnum type() {
        return ProtocolMessageMapEnum.CHAT;
    }

    @Override
    public void process(Connection con, Message message) throws IllegalArgumentException {



    }


}
