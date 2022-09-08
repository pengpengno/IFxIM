package com.ifx.connect.encoder;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProtocolEncoder extends MessageToByteEncoder<Protocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        if (msg == null ){
            log.info(" 传入协议 Protocol 为空");
        }
        assert msg != null;
        out.writeInt(msg.getLength().intValue());
        out.writeBytes(JSON.toJSONBytes(msg));
    }
}
