package com.ifx.connect.handler;

import com.ifx.connect.proto.ProtocolType;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/7
 */
public class MessageTypeIdentify {


    private static Map<ProtocolType, Function<ByteBuf,Void>>  functionMap = new HashMap<>();

    public static void s(){
        ProtocolType.ProtocolMessageEnum protocolMessageEnum = ProtocolType.ProtocolMessageEnum.forNumber(1);

    }
}
