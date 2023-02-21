package com.ifx.account;

import com.ifx.account.service.reactive.ReactiveAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/15
 */
@Service
public class AccountController implements Consumer<HttpServerRoutes> {
    @Resource
    private ReactiveAccountService accountService;

    @Override
    public void accept(HttpServerRoutes routes) {
        routes.get("/{account}",(httpServerRequest, httpServerResponse) -> {
            return httpServerResponse.sendObject(accountService.findByAccount(httpServerRequest.param("account")));
        });
    }


}
