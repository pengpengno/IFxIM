package com.ifx.connect.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/28
 */
@Slf4j
public class TokenParserHandler extends ChannelDuplexHandler {
    private final String token;



    public TokenParserHandler(String token) {
        this.token = token;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            ByteBuf newBuf = ctx.alloc().buffer(buf.readableBytes() + token.length() + 1);
            newBuf.writeByte(token.length());
            newBuf.writeBytes(token.getBytes());
            newBuf.writeByte('|');
            newBuf.writeBytes(buf);
            msg = newBuf;
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            int tokenLength = buf.readByte();
            byte[] tokenBytes = new byte[tokenLength];
            buf.readBytes(tokenBytes);
            String receivedToken = new String(tokenBytes);
            if (receivedToken.equals(token)) {
                // Token is valid, strip it off and pass the message up the pipeline
                ByteBuf newBuf = ctx.alloc().buffer(buf.readableBytes() - tokenLength - 1);
                buf.skipBytes(1);
                newBuf.writeBytes(buf);
                msg = newBuf;
            } else {
                // Token is not valid, close the connection
                ctx.close();
                return;
            }
        }
        super.channelRead(ctx, msg);
    }

}
