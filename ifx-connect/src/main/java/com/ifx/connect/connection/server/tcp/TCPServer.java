package com.ifx.connect.connection.server.tcp;

import com.ifx.connect.connection.server.ServerLifeStyle;
import com.ifx.connect.handler.client.ClientChannelInitializer;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.net.InetSocketAddress;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/28
 */
public class TCPServer implements ServerLifeStyle {



    @Override
    public void start() {

//        DisposableServer server = TcpServer.create()
//                .host("localhost")
//                .port(SERVER_PORT)
//                .handle((inbound, outbound) -> {
//                    String userId = inbound.attr(USER_ID_KEY).get();
//                    // Do something with the user ID and the TCP connection
//                    return inbound.receive().then();
//                })
//                .bind()
//                .block();

    }


    public void start(InetSocketAddress socketAddress) {
        DisposableServer server = TcpServer.create()
                .host(socketAddress.getHostString())
                .port(socketAddress.getPort())
                .doOnChannelInit((connectionObserver, channel, remoteAddress) -> channel.pipeline().addLast(new ClientChannelInitializer()))
//                .doOnConnection(connection -> connection.channel().)
                .handle((inbound, outbound) -> {
//                    inbound.withConnection(connection ->  connection.channel().attr())
//                    inbound.withConnection((connection -> connection.channel().attr()))
//                    String userId = inbound.(USER_ID_KEY).get();
                    // Do something with the user ID and the TCP connection
                    return inbound.receive().then();
                })
                .bind()
                .block();

    }


    @Override
    public void stop() {

    }
}
