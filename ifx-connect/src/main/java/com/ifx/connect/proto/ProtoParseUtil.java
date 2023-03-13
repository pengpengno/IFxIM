package com.ifx.connect.proto;

import com.google.protobuf.Message;
import com.ifx.connect.enums.MessageMapEnum;
import io.netty.buffer.ByteBuf;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/13
 */
public class ProtoParseUtil {



    public static ByteBuf parseMessage2ByteBuf(Message message,ByteBuf buffer){
        if (null != message){
            MessageMapEnum mapEnum = MessageMapEnum.getByMessageClass(message.getClass());
            int type = mapEnum.getTypeEnum().getNumber();
            byte[] bytes = message.toByteArray();
            int length = bytes.length;
            if (null != buffer){
                buffer.writeInt(length);
                buffer.writeInt(type);
                buffer.writeBytes(bytes);
            }
        }
        return buffer;

    }
}
