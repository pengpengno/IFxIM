package com.ifx.client.api;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.ifx.account.route.chat.ChatRoute;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.connect.connection.client.ReactiveClientAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ChatApi {


    @Autowired
    WebClient webClient;


    public Mono<Void> sendMsg(ChatMsgVo chatMsgVo){

        return webClient
                .post()
                .uri(ChatRoute.CHAT_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatMsgVo)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                    (clientResponse ) ->
                        clientResponse
                            .bodyToMono(ProblemDetail.class)
                            .flatMap(problemDetail ->
                            Mono.error(()->
                            new RuntimeException(problemDetail.getDetail()))))
                .bodyToMono(Void.class)
                .doOnError((throwable)-> {
                    log.error(ExceptionUtil.stacktraceToString(throwable) );
                });
    }

}
