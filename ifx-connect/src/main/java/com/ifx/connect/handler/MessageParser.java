package com.ifx.connect.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.connection.server.context.IConnection;
import com.ifx.connect.enums.MessageMapEnum;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;
import com.ifx.connect.proto.ProtocolType;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
public class MessageParser {

    private static final Map<ProtocolType.ProtocolMessageEnum,Parser<? extends Message>> parseMap = new HashMap<>(MessageMapEnum.values().length) ;
    private static final Map<ProtocolType.ProtocolMessageEnum,Parser<? extends Message>> processMap = new HashMap<>(MessageMapEnum.values().length) ;




    static {
        parseMap.put(ProtocolType.ProtocolMessageEnum.CHAT, Chat.ChatMessage.parser());
        parseMap.put(ProtocolType.ProtocolMessageEnum.ACCOUNTINFO, Account.AccountInfo.parser());
        parseMap.put(ProtocolType.ProtocolMessageEnum.AUTH, Account.Authenticate.parser());
    }


    public static Message parseMessage(ProtocolType.ProtocolMessageEnum messageEnum,byte[] bytes) {
        try{
            Message message = parseMap.get(messageEnum).parseFrom(bytes);
            return message;
        }catch (InvalidProtocolBufferException ex ){
            return null;
        }
    }

    public static Message byteBuf2Message(ByteBuf byteBuf){
            try{
                int length = byteBuf.readInt();

                int type = byteBuf.readInt();

                ProtocolType.ProtocolMessageEnum messageEnum = ProtocolType.ProtocolMessageEnum.forNumber(type);
//                MessageMapEnum byEnum = MessageMapEnum.getByEnum(protocolMessageEnum);
//                byEnum.getMessageClass()

                byte[] bytes;

                if (byteBuf.hasArray()) {  //  jvm  heap byteBuf 处理

                    bytes = byteBuf.array();

                } else {  //  memory  byteBuf 处理

                    bytes = new byte[length];

                    byteBuf.getBytes(byteBuf.readerIndex(),bytes);

                }
                Message message = parseMessage(messageEnum, bytes);

                return message;

            }catch (Exception exception){

                return null;

            }

    }
    @FunctionalInterface
    public interface MessageProcess{
        IConnectContextAction contextAction = ServerToolkit.contextAction();

        public void process(IConnection connection , Message message) ;

        default void auth(Message message){
            if (message.getClass() == Account.AccountInfo.class){
                Account.AccountInfo accountInfo = (Account.AccountInfo) message;

//            contextAction.putConnection()

            }
        }

    }
}
