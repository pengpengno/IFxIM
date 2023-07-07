package com.ifx.account.controller;

import org.springframework.context.annotation.Configuration;

/**
 * account router
 * @author pengpeng
 * @description
 * @date 2023/3/3
 */
@Configuration
public class AccountRouter {

//    @Bean
//    public RouterFunction<ServerResponse> accountLoginRouter(ReactiveAccountService accountService) {
//        return RouterFunctions
//                .route(POST(AccRoute.ACCOUNT_GET).and(contentType(APPLICATION_JSON)).and(accept(APPLICATION_JSON)),
//                    request -> accountService.findByAccount(request.pathVariable("account"))
//                        .flatMap(re -> ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromValue(re))))
//                ;
//    }


}
