package com.ifx.connect.handler.client;

import com.ifx.connect.proto.ProtocolType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientChannelInitializer extends ChannelInitializer<Channel> {

    private String jwt;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {

//        ch.pipeline()
//                .addLast(Guice.createInjector().getInstance(ProtocolEncoder.class))
//                .addLast(new ProtocolDecoder())

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(jwt);
        Channel channel = ctx.channel();
        Auth.Authenticate auth = Auth.Authenticate.newBuilder()
                .setJwt(jwt)
                .build();
        try {
            byte[] bytes = auth.toByteArray();
            int length = bytes.length;
            ByteBuf buffer = channel.alloc().buffer(8+ length);
//            ByteBuf buf = Unpooled.buffer(8 + length);
            buffer.writeInt(length);
            buffer.writeInt(ProtocolType.ProtocolMessageEnum.AUTH_VALUE);
            buffer.writeBytes(bytes);
//            out.writeBytes(buf);
            channel.writeAndFlush(buffer);
            buffer.release();
        } catch (Exception e) {
            log.error("[client] msg encode has error", e);
        }
        super.channelActive(ctx);
    }

    @Override
    public boolean isSharable() {
        return super.isSharable();
    }
}
