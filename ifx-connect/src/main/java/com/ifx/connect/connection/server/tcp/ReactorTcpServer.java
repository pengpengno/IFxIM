package com.ifx.connect.connection.server.tcp;

import com.google.inject.Singleton;
import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import com.ifx.connect.handler.server.ServerInboundHandler;
import io.netty.channel.Channel;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.tcp.TcpServer;

import java.net.InetSocketAddress;

/**
 * 响应式 tcp 链接
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
@Slf4j
@Singleton
public class ReactorTcpServer implements ReactiveServer {


    private enum SingleInstance{
        INSTANCE;
        private final ReactiveServer instance;
        SingleInstance(){
            instance = new ReactorTcpServer();
        }
        private ReactiveServer getInstance(){
            return instance;
        }
    }
    public static ReactiveServer getInstance(){
        return ReactorTcpServer.SingleInstance.INSTANCE.getInstance();
    }


    private IConnectContextAction contextAction ;

    private ReactorTcpServer(){
        contextAction = ServerToolkit.contextAction();
    }

    @Override
    public void start(InetSocketAddress address) {
        create(address);
    }

    @Override
    public void stop() {

    }

    public  void create(InetSocketAddress address){
        TcpServer niceDone = TcpServer
                .create()
                .wiretap("tcp-server", LogLevel.INFO)
//                .bindAddress(()-> address)
                .host(address.getHostName())
                .port(address.getPort())
                .doOnChannelInit((connectionObserver, channel, remoteAddress) -> {
//                    channel.pipeline().addFirst(new ServerChannelInitializer());
                })
                .doOnConnection(conn -> {
                    Channel channel = conn.channel();
                    channel.pipeline()
//                            .addLast(new ServerChannelInitializer())
                            .addFirst(new ServerInboundHandler());
//
//                    AccountInfo accountInfo = channel.attr(TokenParserHandler.AccAttr).get();
//                    IConnection build = ReactorConnection.builder()
//                            .connection(conn)
//                            .accountInfo(accountInfo)
//                            .channel(channel)
//                            .build();
//                    // Remove the connection when it's idle
//                    conn.onReadIdle(36000 , ()-> contextAction.closeAndRmConnection(accountInfo.getAccount()));
//                    // Store the connection for later use
//                    contextAction.putConnection(build);
//                    conn.onDispose().subscribe(v -> {
//                        // Remove the connection when it's closed
//                        contextAction.closeAndRmConnection(accountInfo.getAccount());
//                    });
                })
//                .handle((nettyInbound, nettyOutbound) -> nettyInbound.receive().asString().doOnNext(log::info).then())
        ;
        log.info("startup netty  on port {}",address.getPort());
        niceDone.bindNow().onDispose().block();

    }

}
