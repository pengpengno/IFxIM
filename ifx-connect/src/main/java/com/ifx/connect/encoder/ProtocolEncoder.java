package com.ifx.connect.encoder;

import com.alibaba.fastjson2.JSON;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
@ChannelHandler.Sharable
public class ProtocolEncoder extends MessageToByteEncoder<Protocol> {

    public static Byte BUS_FLAG = 2 ;

    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        if (msg == null ){
            log.info(" 传入协议 Protocol 为空");
            return;
        }
        byte[] bytes = JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8);
        out.writeByte(BUS_FLAG);
        out.writeInt(bytes.length);
        log.info("传输数据大小为 {} bytes",bytes.length);
        out.writeBytes(bytes);
    }
}
