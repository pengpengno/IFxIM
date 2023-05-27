package com.ifx.account.controller;

import com.ifx.account.route.chat.SessionRoute;
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

    @PostMapping("/init")
    public Mono<SessionInfoVo> init(@RequestBody SessionInfoVo sessionInfoVo){
        return sessionLifeStyle.init(sessionInfoVo.getSessionName());
    }


    @GetMapping()
    public Flux<SessionInfoVo> sessionInfo(@RequestParam Long userId){
        return sessionLifeStyle.findSessionInfoByUserId(userId);
    }


    @PostMapping()
    public Flux<Long> addAccount(@RequestBody SessionAccountVo sessionAccountVo){
        return sessionLifeStyle.addAccount(sessionAccountVo);
    }

}
