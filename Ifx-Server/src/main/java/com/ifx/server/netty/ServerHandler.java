package com.ifx.server.netty;

import com.alibaba.fastjson2.JSONObject;
import com.ifx.connect.proto.Protocol;
import com.ifx.server.invoke.ServerProtoReceive;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerHandler extends ChannelDuplexHandler {
    @Resource(name = "serverPool")
    private ExecutorService serverService;
    @Resource
    private ServerProtoReceive serverProtoReceive;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//        TODO  implents netty serial   will use Extueor do work
        //处理收到的数据，并反馈消息到到客户端
        ByteBuf in = (ByteBuf) msg;
        String req = in.toString(CharsetUtil.UTF_8);
        Protocol protocol = JSONObject.parseObject(req, Protocol.class);
        log.info("收到客户端发过来的消息: {}" , req);
//        ctx.writeAndFlush()
        log.info("receive msg from server-side {}, data package {}",ctx.channel().localAddress().toString(),protocol);
        serverService.submit(()-> serverProtoReceive.received(ctx,protocol));
        //写入并发送信息到远端（客户端）
//        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是服务端，我已经收到你发送的消息", CharsetUtil.UTF_8));
    }
    @SneakyThrows
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {

        //出现异常的时候执行的动作（打印并关闭通道）
        cause.printStackTrace();
        ctx.close();
        throw cause;
    }


}
