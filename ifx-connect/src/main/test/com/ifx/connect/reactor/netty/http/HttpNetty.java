package com.ifx.connect.reactor.netty.http;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */
public class HttpNetty {
    @Test
    public void createTest(){
        DisposableServer server =
                HttpServer.create()
                        .port(8003)
                        .route(routes ->
                        routes.get("/hello",
                                    (request, response) -> response.sendString(Mono.just("Hello World!")))
                                .get("/",(request,response)-> response.sendString(Mono.just("myTest")))
                            .post("/echo",
                                    (request, response) -> response.send(request.receive().retain()))
                            .get("/path/{param}",
                                    (request, response) -> response.sendString(Mono.just(request.param("param"))))
                            .ws("/ws",
                                    (wsInbound, wsOutbound) -> wsOutbound.send(wsInbound.receive().retain())))
                        .bindNow();

        server.onDispose()
                .block();
    }
}
