package com.ifx.connect.connection.server.tcp;

import com.google.inject.Singleton;
import com.ifx.connect.spi.ReactiveHandlerSPI;
import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.DisposableServer;
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


    private InetSocketAddress address;
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
    private TcpServer server;
    private DisposableServer disposableServer;

    private ReactorTcpServer(){
        contextAction = ServerToolkit.contextAction();
    }

    @Override
    public void start(InetSocketAddress address) {
        create(address);
        start();
    }

    @Override
    public void stop() {
        disposableServer.disposeNow();
    }

//    public void

    public  void create(InetSocketAddress address){
        this.address = address;
        server = TcpServer
                .create()
                .wiretap("tcp-server", LogLevel.INFO)
                .host(address.getHostName())
                .port(address.getPort())
                .handle(ReactiveHandlerSPI.wiredSpiHandler().handler())
        ;
        log.info("startup netty  on port {}",address.getPort());

    }

    public void start(){
        disposableServer = server.bindNow();
        disposableServer.onDispose().block();
    }



}
