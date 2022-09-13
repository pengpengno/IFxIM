package com.ifx.connect.encoder;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
//@Component
@ChannelHandler.Sharable
public class ProtocolEncoder extends MessageToByteEncoder<Protocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        if (msg == null ){
//            log.info(" 传入协议 Protocol 为空");
            return;
        }
        assert msg != null;
        byte[] bytes = JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        log.info("传输数据大小为 {} bytes",bytes.length);
        out.writeBytes(bytes);
    }
}
