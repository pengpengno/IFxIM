package com.ifx.connect.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.ifx.connect.enums.MessageMapEnum;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;
import com.ifx.connect.proto.ProtocolType;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息 解析序列化
 * @author pengpeng
 * @description
 * @date 2023/3/14
 */
@Slf4j
public class MessageParser {

    private static final Map<ProtocolType.ProtocolMessageEnum,Parser<? extends Message>> parseMap = new HashMap<>(MessageMapEnum.values().length) ;


    static {
        parseMap.put(ProtocolType.ProtocolMessageEnum.CHAT, Chat.ChatMessage.parser());
        parseMap.put(ProtocolType.ProtocolMessageEnum.ACCOUNTINFO, Account.AccountInfo.parser());
        parseMap.put(ProtocolType.ProtocolMessageEnum.AUTH, Account.Authenticate.parser());
    }


    public static Message parseMessage(ProtocolType.ProtocolMessageEnum messageEnum,byte[] bytes) throws InvalidProtocolBufferException{
        Message message = parseMap.get(messageEnum).parseFrom(bytes);
        return message;
    }

    /***
     * 将 message 写入指定 byteBuf
     * @param message 待网络传入的 message
     * @param byteBuf byteBuf 容器
     * @return 写入数据后的 byteBuf 容器
     */
    public static ByteBuf message2ByteBuf(Message message,ByteBuf byteBuf){
        MessageMapEnum mapEnum = MessageMapEnum.getByMessageClass(message.getClass());
        byte[] bytes = message.toByteArray();
        int length = bytes.length;
        int type = mapEnum.getTypeEnum().getNumber();
        byteBuf.writeInt(length);
        byteBuf.writeInt(type);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    /***
     * 读取 byteBuf 中的 message 数据
     * @param byteBuf
     * @return 解析 byteBuf 后的 Message 数据
     * @throws IllegalAccessException 给定 byteBuf 不可读
     */
    public static Message byteBuf2Message(ByteBuf byteBuf) throws IllegalAccessException {
        if (null == byteBuf || byteBuf.readableBytes() <= 0){
            throw new IllegalAccessException(" The specify  byteBuf is valid or  unreadable!");
        }
            try{
                int length = byteBuf.readInt();

                int type = byteBuf.readInt();

                ProtocolType.ProtocolMessageEnum messageEnum = ProtocolType.ProtocolMessageEnum.forNumber(type);

                if (messageEnum == null){
                    throw new IllegalAccessException("The send data package is null of type, pls refer to the  ProtocolMessageEnum!");
                }
                byte[] bytes;

                if (byteBuf.hasArray()) {  //  jvm  heap byteBuf 处理

                    bytes = byteBuf.array();

                } else {  //  memory  byteBuf 处理

                    bytes = new byte[length];

                    byteBuf.getBytes(byteBuf.readerIndex(),bytes);

                }
                Message message = parseMessage(messageEnum, bytes);

                return message;

            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException){
                log.error("Illegal message ");

                return null;
            }
            catch (Exception exception){

                return null;

            }

    }



}
