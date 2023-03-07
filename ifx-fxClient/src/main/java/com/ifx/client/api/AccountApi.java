package com.ifx.client.api;

import com.ifx.account.route.accout.AccRoute;
import com.ifx.account.vo.AccountAuthenticateVo;
import com.ifx.account.vo.AccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
@Component
@Slf4j
public class AccountApi {

    @Autowired
    public WebClient webClient;

    public Mono<AccountAuthenticateVo> auth (AccountVo accountVo) {
       return webClient
                .post()
                .uri(AccRoute.ACCOUNT_ROUTE+AccRoute.AUTH_POST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(accountVo)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        (clientResponse ) ->
                                clientResponse
                                        .bodyToMono(ProblemDetail.class)
                                        .flatMap(problemDetail ->
                                                Mono.error(()->
                                                        new RuntimeException(problemDetail.getDetail()))))
                .bodyToMono(AccountAuthenticateVo.class)
                .doOnError((throwable)-> {
                    log.error( throwable.getMessage());
                });
    }
}
