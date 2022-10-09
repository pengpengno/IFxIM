package com.ifx.connect.decoder;

import com.alibaba.fastjson2.JSON;
import com.ifx.connect.encoder.ProtocolEncoder;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class ProtocolDecoder extends ByteToMessageDecoder {


    private static final int PROTOCOL_HEADER_SIZE = 4;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in == null){
            return;
        }
//        TODO 待添加业务表示区分是否为netty 通讯
        int flag = in.readByte();
        if (flag == ProtocolEncoder.BUS_FLAG){
            int length = in.readInt();
            if (in.readableBytes() >= length){
                ByteBuf byteBuf = in.readBytes(length);
                log.info("接受到的数据大小为 {} bytes",length);
                Protocol protocol = JSON.parseObject(byteBuf.toString(StandardCharsets.UTF_8), Protocol.class);
//                Protocol protocol = (Protocol) byteBuf.toString(StandardCharsets.UTF_8);
                out.add(protocol);
            }
        }
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null){
            return null;
        }
        int length = in.readInt();
        ByteBuf byteBuf = in.readBytes(length);
        Protocol protocol = JSON.parseObject(byteBuf.toString(StandardCharsets.UTF_8), Protocol.class);
        return protocol;
//        return super.decode(ctx, in);
    }
}
