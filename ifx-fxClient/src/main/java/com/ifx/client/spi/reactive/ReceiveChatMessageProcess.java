package com.ifx.client.spi.reactive;

import com.google.protobuf.Message;
import com.ifx.client.app.controller.MainController;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.connect.enums.ProtocolMessageMapEnum;
import com.ifx.connect.proto.Chat;
import com.ifx.connect.spi.netty.ProtoBufProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.netty.Connection;
@Slf4j
@Service
public class ReceiveChatMessageProcess implements ProtoBufProcess {


    @Autowired
    MainController mainController;

    @Override
    public ProtocolMessageMapEnum type() {
        return ProtocolMessageMapEnum.CHAT;
    }

    @Override
    public void process(Connection con, Message message) throws IllegalArgumentException {

        if (null != message && con != null){

            if (message instanceof  Chat.ChatMessage){

                Chat.ChatMessage chatMessage =  (Chat.ChatMessage)message;

                log.info("receive message  from server ! fire event ");

                ChatEvent chatEvent = new ChatEvent(ChatEvent.RECEIVE_CHAT, chatMessage);
//                TODO 统一接受通知方法
                mainController.receiveEvent(chatEvent);

//                EventUtil.fireEvent( chatEvent );

            }else {
                log.warn("Invalid message ");
            }

            return;
        }

        throw  new IllegalArgumentException("The connection and message is invalid!");



    }
}
