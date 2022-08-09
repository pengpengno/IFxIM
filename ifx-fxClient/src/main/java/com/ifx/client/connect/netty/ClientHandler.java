package com.ifx.client.connect.netty;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.task.Task;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Resource(name = "clientPool")
    private ExecutorService clientServer;
    @Resource
    private ClientAction clientAction;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        /**
         * @Description  处理接收到的消息
         **/
        log.info("receive msg from server-side {}, data package {}",ctx.channel().localAddress().toString(),byteBuf);
        String res = byteBuf.toString(CharsetUtil.UTF_8);
        Protocol protocol = JSONObject.parseObject(res, Protocol.class);
        clientServer.submit(() -> clientAction.getTask(protocol).doTask(protocol));
        super.channelRead(ctx,byteBuf);

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        /**
         * @Description  处理I/O事件的异常
         **/
        log.error(ExceptionUtil.getMessage(cause));
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("【netty】 已连接Server {}" ,ctx.channel().remoteAddress().toString());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
//        if(evt instanceof  )
    }
}