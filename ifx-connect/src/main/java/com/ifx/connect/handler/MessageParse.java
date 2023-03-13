package com.ifx.connect.handler;

import com.google.protobuf.Message;
import com.ifx.connect.proto.ProtocolType;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/10
 */
@FunctionalInterface
public interface MessageParse< U extends ProtocolType.ProtocolMessageEnum,O extends Message> {


    O parseMessage(byte[] bytes,U u,Class<O> oClass);


}
