package com.ifx.connect.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//        TODO  implents netty serial
        //处理收到的数据，并反馈消息到到客户端
        ByteBuf in = (ByteBuf) msg;
        System.out.println("收到客户端发过来的消息: " + in.toString(CharsetUtil.UTF_8));
        log.info("收到客户端发过来的消息: {}" , in.toString(CharsetUtil.UTF_8));
        //写入并发送信息到远端（客户端）
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是服务端，我已经收到你发送的消息", CharsetUtil.UTF_8));

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        //出现异常的时候执行的动作（打印并关闭通道）
        cause.printStackTrace();
        ctx.close();
    }

}
