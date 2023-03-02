package com.ifx.account.webflux.Controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/10
 */
@RequestMapping
public class AccountController {



    @RequestMapping(method = RequestMethod.GET,path = "/s")
    public Mono<String> getAccount(){
        return Mono.just("my first webFlux");
    }




    @RequestMapping(method = RequestMethod.GET,path = "/acc")
    public Mono<String> testAcc(@Validated Acc acc){
        return Mono.just("my first webFlux");
    }




}
