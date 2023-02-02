package com.ifx.server.netty;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.connect.proto.Protocol;
import com.ifx.server.invoke.ServerProtoReceive;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * 服务端解析服务处理
 */
@Slf4j
//@Component
@ChannelHandler.Sharable
public class ServerServiceParseHandler extends ChannelDuplexHandler {
    @Resource(name = "serverPool")
    private ExecutorService serverService;

    @Resource
    private ServerProtoReceive serverProtoReceive;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Protocol protocol = (Protocol) msg;

        log.info("收到客户端发过来的消息: {}" , JSONObject.toJSONString(protocol));

        log.debug("receive msg from server-side {}, data package {}",ctx.channel().localAddress().toString(),protocol);

        serverService.submit(()-> serverProtoReceive.received(ctx,protocol));
        //写入并发送信息到远端（客户端）
        super.channelRead(ctx, msg);
    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @SneakyThrows
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        //出现异常
        log.error("business error {}  ", ExceptionUtil.stacktraceToString(cause));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug(" 开启了 来自 {}的链接请求，channel 已打开 ",ctx.channel().remoteAddress());
        log.debug("登录成功 {} ",ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }
}
