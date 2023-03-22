package com.ifx.account.controller;

import com.ifx.account.chat.SessionRoute;
import com.ifx.account.service.ISessionLifeStyle;
import com.ifx.account.vo.session.SessionAccountVo;
import com.ifx.account.vo.session.SessionInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(SessionRoute.SESSION_ROUTE)
@Slf4j
@Validated
public class SessionController {

    @Autowired
    private ISessionLifeStyle sessionLifeStyle;

    @GetMapping(SessionRoute.SESSION)
    public Mono<SessionInfoVo> init(@RequestBody SessionInfoVo sessionInfoVo){
        return sessionLifeStyle.init(sessionInfoVo.getSessionName());
    }



    @PostMapping(SessionRoute.SESSION)
    public Flux<Long> addAccount(@RequestBody SessionAccountVo sessionAccountVo){
        return sessionLifeStyle.addAccount(sessionAccountVo);
    }

}
