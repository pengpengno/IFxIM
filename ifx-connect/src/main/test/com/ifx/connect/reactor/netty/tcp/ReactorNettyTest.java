package com.ifx.connect.reactor.netty.tcp;

import com.ifx.connect.handler.client.ClientNettyHandler;
import com.ifx.connect.handler.decoder.ProtocolDecoder;
import com.ifx.connect.handler.encoder.ProtocolEncoder;
import io.netty.channel.Channel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.Connection;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpClient;
import reactor.netty.tcp.TcpServer;
import reactor.netty.tcp.TcpSslContextSpec;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * 异步线程处理
 * @author pengpeng
 * @date 2023/1/6
 */
@Slf4j
public class ReactorNettyTest {


    public  static String  HOST = "127.0.0.1";
    public  static Integer  PORT = 9087;
    public static String TCPLogger = "TCPLogger";
    @Test
    public void createServer(){
        DisposableServer server =
                TcpServer.create()
                        .port(PORT)
//                        .handle((inbound, outbound) ->{
//                            log.info("接受到了数据 {}",inbound.receive().asString(Charset.defaultCharset()));
//                            return outbound.sendString(Mono.just("hello"));
//                                })
//                        .doOnUnbound(disposableServer -> log.info("端口 {}",disposableServer.address()))
                        .bindNow()
                        ;
//        server.dispose();
        server.onDispose()
                .block();
        log.info("sss");
    }

    @Test
    public void teant (){
        TcpClient client =
                TcpClient.create()
                        .host(HOST)
                        .port(PORT)
                        .doOnConnected(connection ->{
                                    connection.addHandlerLast(
                                            new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                                    log.info("处理连接");
                                    return;
                                })
                        ;

//                        .wiretap(WIRETAP)
                ;
        log.info("开始连接");
        Connection conn = client.connectNow();

//        conn.inbound()
//                .receive()
//                .asString(StandardCharsets.UTF_8)
//                .doOnNext(log::info)
//                .subscribe();

    }
    @Test
    public void  createClient() {
        Connection connect = TcpClient.create()
                .host(HOST)
                .port(PORT)
//                .handle((nettyInbound, nettyOutbound) ->  nettyOutbound.sendString(Mono.just("weclome")))
                .connectNow();
        connect.outbound().sendString(Mono.just("sdasdasdsadsadas"));

        connect.inbound()
                .receive()
                .asString(StandardCharsets.UTF_8)
                .doOnNext(log::info)
                .subscribe();
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        while (scanner.hasNext()) {
            String text = scanner.nextLine();
            connect.outbound()
                    .sendString(Mono.just(text + "\r\n"))
                    .then()
                    .subscribe();
            if ("bye".equalsIgnoreCase(text)) {
                break;
            }

        }
        log.info("{}", connect.isDisposed());


    }

    public static void main(String[] args) {
        Connection connect = TcpClient.create()
                .host(HOST)
                .port(PORT)
                .handle((nettyInbound, nettyOutbound) ->  nettyOutbound.sendString(Mono.just("weclome")))
                .connectNow();
//        什么玩意  连接上了 后就自动断开/？？？？？？？？？？？？？？/
//        connect.outbound().sendString(Mono.just("sdasdasdsadsadas"));

//        connect.inbound()
//                .receive()
//                .asString(StandardCharsets.UTF_8)
//                .doOnNext(log::info)
//                .subscribe();
        log.info("{}", connect.isDisposed());

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        while (scanner.hasNext()) {
            String text = scanner.nextLine();
            connect.outbound()
                    .sendString(Mono.just(text + "\r\n"))
                    .then()
                    .subscribe();
            if ("bye".equalsIgnoreCase(text)) {
                break;
            }

        }
    }

//    static final boolean SECURE = System.getProperty("secure") != null;
//    static final int PORT = Integer.parseInt(System.getProperty("port", SECURE ? "8443" : "8080"));
//    static final boolean WIRETAP = System.getProperty("wiretap") != null;
    @Test
    public  void rrr() {
        TcpClient client =
                TcpClient.create()
                        .port(PORT)
//                        .wiretap(WIRETAP)
                ;

//        if (SECURE) {
            TcpSslContextSpec tcpSslContextSpec =
                    TcpSslContextSpec.forClient()
                            .configure(builder -> builder.trustManager(InsecureTrustManagerFactory.INSTANCE));
            client = client.secure(spec -> spec.sslContext(tcpSslContextSpec));
//        }

        Connection connection =
                client.handle((in, out) -> out.send(Flux.concat(ByteBufFlux.fromString(Mono.just("echo")),
                                in.receive().retain())))
                        .connectNow();

        connection.onDispose()
                .block();
    }

    @Test
    public void customServer(){
        AtomicReference<Channel> che = null;

        Mono<? extends DisposableServer> bind = TcpServer.create()
                .wiretap(TCPLogger, LogLevel.DEBUG, AdvancedByteBufFormat.SIMPLE)
                .doOnConnection(connection -> {
                    connection.addHandlerFirst(new ProtocolEncoder());
                })
                .doOnChannelInit((connectionObserver, channel, remoteAddress) -> {
                    channel.pipeline()
                            .addLast(new ProtocolEncoder())
                            .addLast(new ProtocolDecoder())
                            .addLast(new ClientNettyHandler());
                    che.set(channel);
                })
                .bindAddress((Supplier<? extends SocketAddress>) () -> {
                    return new InetSocketAddress("127.0.0.1", 9081);
                })
                .bind();
        che.get().writeAndFlush("sds");
//        bind.
//        Disposable tcpServerDis = bind;

//                .subscribe();
//        tcpServerDis.

    }
}
