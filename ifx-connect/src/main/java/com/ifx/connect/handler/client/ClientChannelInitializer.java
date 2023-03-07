package com.ifx.connect.handler.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
public class ClientChannelInitializer extends ChannelInitializer<Channel> {


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
    public boolean isSharable() {
        return super.isSharable();
    }
}
