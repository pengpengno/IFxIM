package com.ifx.connect.handler.client;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.connect.config.thread.ClientThreadPool;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.spi.ClientBusinessSPI;
import com.ifx.connect.spi.ModuleSPILoader;
import com.ifx.connect.task.TaskManager;
import com.ifx.exec.ex.bus.IFXException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.ServiceLoader;

@Slf4j
@ChannelHandler.Sharable
public class ClientBusinessHandler extends SimpleChannelInboundHandler<Protocol> {


    private TaskManager taskManager;

    protected ClientBusinessSPI clientBusinessSPI;

    private ClientThreadPool clientThreadPool;


    static {
        ServiceLoader<ClientBusinessSPI> load = ServiceLoader.load(ClientBusinessSPI.class);
        Iterator<ClientBusinessSPI> iterator = load.iterator();
        if (iterator.hasNext()){
            ClientBusinessSPI next = iterator.next();
//            TODO 系统配置文件指定
        }
    }

    public ClientBusinessHandler() {
        super();
        clientThreadPool = ClientThreadPool.getInstance();
        clientBusinessSPI = ModuleSPILoader.loadUniqueSPI(ClientBusinessSPI.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Protocol protocol) throws Exception {
        /**
         * @Description  处理接收到的消息
         **/
        log.debug("receive msg from server-side {}",ctx.channel().localAddress().toString());
        log.debug("received msg package {}",protocol);
        Mono.just(protocol)
            .doOnNext(pr -> clientThreadPool.threadPool().submit(clientBusinessSPI.doBusiness(pr)))
            .doOnError(throwable -> throwable instanceof  NullPointerException
                        ,(throwable -> Mono.error(new IFXException("客户端业务拓展点尚未加载！"))));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        /**
         * @Description  处理I/O事件的异常
         **/
        log.error(ExceptionUtil.getMessage(cause));
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("[netty] 已连接 Server host{}" ,ctx.channel().remoteAddress().toString());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
