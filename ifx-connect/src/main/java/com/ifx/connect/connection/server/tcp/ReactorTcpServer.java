package com.ifx.connect.connection.server.tcp;

import com.google.inject.Singleton;
import com.ifx.connect.connection.server.ReactiveServer;
import com.ifx.connect.connection.server.ServerToolkit;
import com.ifx.connect.connection.server.context.IConnectContextAction;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpServer;

import java.net.InetSocketAddress;
import java.util.Date;

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
//                    channel.pipeline().addFirst(new ServerInboundHandler());
                })
//                .doOnConnection(conn -> {
//                    log.info("connection start");
//                    Channel channel = conn.channel();
//                    conn.inbound().receive().asString().doOnNext(log::info).subscribe();
//                    int i = channel.alloc().buffer().readableBytes();
//                    AccountInfo accountInfo = channel.attr(ServerInboundHandler.AccAttr).get();
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
//                })
//                .handle((nettyInbound, nettyOutbound) -> nettyInbound.receive().asString().doOnNext(log::info).then())
                .handle((in, out) -> {
                    Flux<String> welcomeFlux =
                            Flux.just("Welcome to "  + "!\r\n", "It is " + new Date() + " now.\r\n");
//                    in.withConnection(connection -> {
//                        ByteBufAllocator alloc = connection.channel().alloc();
////                        alloc.
//                        ByteBuf buffer = connection.channel().alloc().buffer();
//                        if (buffer.readableBytes() > 4) {
//                            int length = buffer.readInt();
//                            int type = buffer.readInt();
//                            log.info("length is  {}   , type is {} ",length,type);
//                        }
//                    });
                    Flux<String> just = Flux.just("233");

                    Flux<String> responses =
                            in.receive()
                                    .asString()
                                    // Signals completion when 'bye' is encountered.
                                    // Reactor Netty will perform the necessary clean up, including
                                    // disposing the channel.
                                    .takeUntil("bye"::equalsIgnoreCase)
                                    .map(text -> {
                                        String response = "Did you say '" + text + "'?";
                                        if (text.isEmpty()) {
                                            response = "Please type something.";
                                        }
                                        else if ("bye".equalsIgnoreCase(text)) {
                                            response = "Have a good day!\r\n";
                                        }
                                        return response;
                                    });
                    Flux<String> test1 = Flux.defer(()-> Mono.just("23"))
                            .handle((te,sink)-> {
                                sink.next(te+"sdasdsadsa");
                            });


                    return out.sendString(Flux.concat(welcomeFlux, test1,just,responses));
                })
        ;
        log.info("startup netty  on port {}",address.getPort());
        niceDone.bindNow().onDispose().block();

    }

}
