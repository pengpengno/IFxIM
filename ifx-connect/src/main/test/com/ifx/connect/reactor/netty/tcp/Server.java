package com.ifx.connect.reactor.netty.tcp;

import com.ifx.connect.connection.server.ServerToolkit;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
@Slf4j
public class Server {
    public  static String  HOST = "127.0.0.1";
    public  static Integer  PORT = 9087;
    public static void main(String[] args) {
        DisposableServer server = TcpServer.create()
                .host("localhost")
                .port(PORT)
                .wiretap("tcp-server", LogLevel.DEBUG)
                .handle((inbound, outbound) -> {
                    // Log any incoming messages

                    Flux<String> objectFlux = Flux.create(fluxSink -> {
                        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
                        while (scanner.hasNext()) {
                            log.info("wite");
                            String text = scanner.nextLine();
                            fluxSink.next(text);
                            if ("bye".equalsIgnoreCase(text)) {
                                break;
                            }
                        }
                        fluxSink.complete();
                    });
                     outbound.sendString(objectFlux);
                    return inbound.receive()
                            .asString()
                            .doOnNext(str -> System.out.println("Received: " + str))
                            // Transform the data and send it back to the client
                            .flatMap(str -> outbound.sendString(Mono.just("Echo: " + str)))
                            // Log any errors or exceptions
                            .doOnError(err -> System.err.println("Error handling connection: " + err))
                            .doFinally(onFinally -> System.out.println("Connection closed."));
                })
                .bindNow();

        System.out.println("Server started on port " + server.port());

        // Wait for the server to stop
        server.onDispose().block();
    }

    @Test
    public void startReactiveServer(){

        ServerToolkit.reactiveServer().start(new InetSocketAddress("localhost",8094));
    }
}
