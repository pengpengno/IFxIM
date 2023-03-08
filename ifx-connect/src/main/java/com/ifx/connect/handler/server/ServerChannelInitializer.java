package com.ifx.connect.handler.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
@Slf4j
public class ServerChannelInitializer extends ChannelInitializer<Channel> {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        log.info(" channel is loading ");
        ch.pipeline().addLast(new TokenParserHandler())
//                .addLast(new ProtocolEncoder())
//                .addLast(new ProtocolDecoder())
                ;
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

}
