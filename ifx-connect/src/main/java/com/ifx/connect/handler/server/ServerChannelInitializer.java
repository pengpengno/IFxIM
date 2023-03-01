package com.ifx.connect.handler.server;

import com.ifx.connect.handler.decoder.ProtocolDecoder;
import com.ifx.connect.handler.encoder.ProtocolEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */

public class ServerChannelInitializer extends ChannelInitializer<Channel> {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ch.pipeline().addLast(new LoggingHandler())
                .addLast(new ProtocolEncoder())
                .addLast(new ProtocolDecoder())
                ;
    }
}
