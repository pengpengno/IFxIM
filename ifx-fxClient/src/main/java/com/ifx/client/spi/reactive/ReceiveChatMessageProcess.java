package com.ifx.client.spi.reactive;

import com.google.protobuf.Message;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.connect.enums.ProtocolMessageMapEnum;
import com.ifx.connect.process.ProtoBufProcess;
import com.ifx.connect.proto.Chat;
import com.sun.javafx.event.EventUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.Connection;
@Slf4j
public class ReceiveChatMessageProcess implements ProtoBufProcess {


    @Override
    public ProtocolMessageMapEnum type() {
        return ProtocolMessageMapEnum.CHAT;
    }

    @Override
    public void process(Connection con, Message message) throws IllegalArgumentException {

        if (null != message && con != null){

            ProtocolMessageMapEnum type = type();
            if (message instanceof  Chat.ChatMessage){

                Chat.ChatMessage chatMessage =  (Chat.ChatMessage)message;

                log.info("receive message  from server ! ");
                ChatEvent chatEvent = new ChatEvent(ChatEvent.RECEIVE_CHAT, chatMessage);
                EventUtil.fireEvent( chatEvent );

//                if (con.channel().isActive() && StrUtil.isNotBlank(accountInfo.getAccount())){
//
//                    con.channel().attr(ConnectionConstants.BING_ACCOUNT_KEY).set(accountInfo);
//
//                    ReactorConnection connection = ReactorConnection.builder()
//                            .channel(con.channel())
//                            .connection(con)
//                            .accountInfo(accountInfo)
//                            .build();
//
//                    contextAction.putConnection(connection);
//
//                    log.info(" {}  had bind  the channel !",accountInfo.getAccount());
//
//                    con.outbound().sendString(Mono.justOrEmpty("bind channel  has been established")).then().subscribe();
//
//                }
            }


            return;
        }

        throw  new IllegalArgumentException("The connection and message is invalid!");



    }
}
