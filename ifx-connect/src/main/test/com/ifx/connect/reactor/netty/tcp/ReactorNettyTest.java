package com.ifx.connect.reactor.netty.tcp;

import com.ifx.connect.handler.client.ClientBusinessHandler;
import com.ifx.connect.handler.decoder.ProtocolDecoder;
import com.ifx.connect.handler.encoder.ProtocolEncoder;
import io.netty.channel.Channel;
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
import reactor.test.StepVerifier;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
        DisposableServer server = TcpServer.create()
                .host("localhost")
                .port(PORT)
                .wiretap("tcp-server", LogLevel.INFO)
                .handle((inbound, outbound) -> {
                    // Log any incoming messages
                    outbound.sendString(Mono.just("hello"));
                    return  inbound.receive().then();
//                    return inbound.receive()
//                            .asString()
//                            .doOnNext(str -> System.out.println("Received: " + str))
//                            // Transform the data and send it back to the client
//                            .flatMap(str -> outbound.sendString(Mono.just("Echo: " + str)))
//                            // Log any errors or exceptions
//                            .doOnError(err -> System.err.println("Error handling connection: " + err))
//                            .doFinally(onFinally -> System.out.println("Connection closed."));
                })
                .doOnConnection(l-> log.info(l.address().toString()))
                .bindNow();

        System.out.println("Server started on port " + server.port());

        // Wait for the server to stop
        server.onDispose().block();
    }

    @Test
    public  void  createServer2(){
        DisposableServer disposableServer1 = TcpServer.create()
                .port(PORT)
                .wiretap("SLF4J", LogLevel.DEBUG)
                .handle((inbound, outbound) -> {
//                    inbound.receive().subscribe(k -> {
//                        log.info("接受到了数据 {}", k.toString(Charset.defaultCharset()));
//                    });
                    return outbound.sendString(Mono.just("hello"));
                })
                .doOnUnbound(disposableServer -> log.info("端口 {}", disposableServer.address()))
                .bindNow();
        disposableServer1.onDispose().block();
    }


    @Test
    public void  manyTimes(){
        Connection connection =
                TcpClient.create()
                        .wiretap("client",LogLevel.INFO)
                        .host("localhost")
                        .port(PORT)
                        .handle((inbound, outbound) -> outbound.sendString(Mono.just("geo it")))
                        .connectNow();

        StepVerifier.create(connection.outbound().sendString(Mono.just("s")))
                .verifyComplete();
    }
    public static void main(String[] args) throws InterruptedException {

        Connection connect = TcpClient.create()
                .host(HOST)
                .port(8094)
                .wiretap("client",LogLevel.INFO)
                .handle((nettyInbound, nettyOutbound) -> {
//                    return nettyInbound.receive().asString().doOnNext(l-> log.info(l)).then();
//                    Flux<String> objectFlux = Flux.create(fluxSink -> {
//                        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
//                        while (scanner.hasNext()) {
//                            log.info("wite");
//                            String text = scanner.nextLine();
//                            fluxSink.next(text);
//                            if ("over".equalsIgnoreCase(text)) {
//                                break;
//                            }
//                        }
//                        fluxSink.complete();
//                    });
//                    nettyOutbound.sendString(Mono.just("sdda"));
//                    return nettyOutbound.sendString(objectFlux);
//                    return nettyOutbound.sendString(Mono.just("sdda"));
                    return Mono.never();
                })
                .connectNow();
        log.info("{}", connect.isDisposed());
        connect.inbound().receive().asString().doOnNext(f-> log.info(f)).then().subscribe();
        connect.outbound().sendString(Mono.just("nice to meet you")).then().subscribe();
        connect.onDispose().block();
//        Thread.sleep(100000);
//        StepVerifier.create(connect.outbound().sendString(Mono.just("s")))
//                .verifyComplete();
    }

//    static final boolean SECURE = System.getProperty("secure") != null;
//    static final int PORT = Integer.parseInt(System.getProperty("port", SECURE ? "8443" : "8080"));
//    static final boolean WIRETAP = System.getProperty("wiretap") != null;
    @Test
    public  void localClient() {
        TcpClient client =
                TcpClient.create()
                        .port(PORT)
//                        .wiretap(WIRETAP)
                ;

            TcpSslContextSpec tcpSslContextSpec =
                    TcpSslContextSpec.forClient()
                            .configure(builder -> builder.trustManager(InsecureTrustManagerFactory.INSTANCE));
            client = client.secure(spec -> spec.sslContext(tcpSslContextSpec));

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
                            .addLast(new ClientBusinessHandler());
                    che.set(channel);
                })
                .bindAddress((Supplier<? extends SocketAddress>) () -> {
                    return new InetSocketAddress("127.0.0.1", 9081);
                })
                .bind();
    }
}
