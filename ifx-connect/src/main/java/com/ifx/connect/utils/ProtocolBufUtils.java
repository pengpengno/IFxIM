package com.ifx.connect.utils;

import com.google.protobuf.Message;
import com.ifx.connect.enums.ProtocolMessageMapEnum;
import io.netty.buffer.ByteBuf;

/***
 * protoBuf  工具类
 */
public class ProtocolBufUtils {

    /***
     * Trans proto Message into  netty data context {@link ByteBuf }
     * Basically a byteBuf used for translation  contains headerType , bodyLength , bodyData;
     * @param message Used for generate data context ByteBuf
     * @param byteBuf netty data context
     * @return ByteBuf
     */
    public static ByteBuf protoTrans2ByteBuf(ByteBuf byteBuf , Message message){
        if (message != null && byteBuf != null){

            int writableLength = byteBuf.writableBytes();

            if (writableLength < 0 ){
                throw new IllegalArgumentException("The provided byteBuf has no enough space to fill data!");
            }

            ProtocolMessageMapEnum mapEnum = ProtocolMessageMapEnum.getByClass(message.getClass());

            int headerType = mapEnum.getTypeEnum().getNumber();

            byte[] bodyData = message.toByteArray();

            int bodyLength = bodyData.length;


            byteBuf.writeInt(bodyLength);
            byteBuf.writeInt(headerType);
            byteBuf.writeBytes(bodyData);

            return byteBuf ;
        }
        throw new IllegalArgumentException("The input [Message] is null , pls check it out!");
    }
}
