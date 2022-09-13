package com.ifx.connect.decoder;

import com.alibaba.fastjson.JSON;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

//@Component
@Slf4j
//@ChannelHandler.Sharable
//public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {
public class ProtocolDecoder extends ByteToMessageDecoder {

//    public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
//        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
//    }

    private static final int PROTOCOL_HEADER_SIZE = 4;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in == null){
            return;
        }
//        TODO 待添加业务表示区分是否为netty 通讯
        int length = in.readInt();
        if (in.readableBytes() >= length){
            ByteBuf byteBuf = in.readBytes(length);
            log.info("接受到的数据大小为 {} bytes",length);
            Protocol protocol = JSON.parseObject(byteBuf.toString(StandardCharsets.UTF_8), Protocol.class);
            out.add(protocol);
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
