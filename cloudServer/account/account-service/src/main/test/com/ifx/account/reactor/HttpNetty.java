package com.ifx.account.reactor;

import com.ifx.account.AccountApplication;
import com.ifx.account.service.reactive.ReactiveAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */
@Slf4j
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountApplication.class)
public class HttpNetty {
//    @Resource
//    private AccountController accountController;
    private ReactiveAccountService accountService;

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

    @Test
    public void createRouteServer(){
        HttpServerRoutes httpServerRoutes = HttpServerRoutes.newRoutes().get("/path",((httpServerRequest, httpServerResponse) -> httpServerResponse.sendString(Mono.just("sds"))));
        DisposableServer server =
                HttpServer.create()
                        .port(8003)
                        .route(routes -> {
                            routes.get("/{account}",(httpServerRequest, httpServerResponse) -> {
                                String account = httpServerRequest.param("account");
                                return httpServerResponse.sendObject(accountService.findByAccount(account));
                            });
                        })
                        .bindNow();

        server.onDispose()
                .block()
        ;
    }
}
