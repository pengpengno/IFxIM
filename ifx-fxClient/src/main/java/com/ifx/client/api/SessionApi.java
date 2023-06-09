package com.ifx.client.api;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.account.route.chat.ChatRoute;
import com.ifx.account.route.chat.SessionRoute;
import com.ifx.account.vo.session.SessionInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SessionApi {


    @Autowired
    WebClient webClient;
    public Flux<SessionInfoVo> sessionInfo(Long userId){
//        UriComponentsBuilder.queryParam("userId", userId)
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(SessionRoute.SESSION_ROUTE)
                        .queryParam("userId",userId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                    (clientResponse ) ->
                        clientResponse
                            .bodyToMono(ProblemDetail.class)
                            .flatMap(problemDetail ->
                                Mono.error(()->
                                    new RuntimeException(problemDetail.getDetail()))))
                .bodyToFlux(SessionInfoVo.class)
                .doOnError((throwable)-> {
                    log.error(ExceptionUtil.stacktraceToString(throwable) );
                });
    }


    public Mono<SessionInfoVo> sessionInfoBySessionId(Long sessionId){
//        UriComponentsBuilder.queryParam("userId", userId)
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(SessionRoute.SESSION_ROUTE+"/info")
                        .queryParam("sessionId",sessionId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                    (clientResponse ) ->
                        clientResponse
                            .bodyToMono(ProblemDetail.class)
                            .flatMap(problemDetail ->
                                Mono.error(()->
                                    new RuntimeException(problemDetail.getDetail()))))
                .bodyToMono(SessionInfoVo.class)
                .doOnError((throwable)-> {
                    log.error(ExceptionUtil.stacktraceToString(throwable) );
                });
    }

}
