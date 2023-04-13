package com.ifx.server.service;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.enums.ProtocolMessageMapEnum;
import com.ifx.connect.process.ProtoBufProcess;
import com.ifx.connect.proto.Chat;
import org.springframework.stereotype.Component;
import reactor.netty.Connection;

/**
 * chat message process
 */
@Component
public class ChatProcessService implements ProtoBufProcess {

    @Override
    public ProtocolMessageMapEnum type() {
        return ProtocolMessageMapEnum.CHAT;
    }

    @Override
    public void process(Connection con, Message message) {

            Chat.ChatMessage chatMessage = (Chat.ChatMessage) message;
            Any chat = chatMessage.getChat();
            IConnectContextAction contextAction = ServerToolkit.contextAction();

    }
}
