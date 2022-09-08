package com.ifx.connect.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import javax.annotation.Resource;
import java.util.List;

@Resource
public class ProtocolDecoder extends ByteToMessageDecoder {

    private static final int PROTOCOL_HEADER_SIZE = 4;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readInt();

//        in.readableBytes(length)

    }
}
