package com.ifx.session.controller;

import com.ifx.session.route.chat.SessionRoute;
import com.ifx.session.service.ISessionLifeStyle;
import com.ifx.session.vo.session.SessionAccountVo;
import com.ifx.session.vo.session.SessionInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("account")
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



    @GetMapping(SessionRoute.SESSION)
    public Flux<Long> addAccount(@RequestBody SessionAccountVo sessionAccountVo){
        return sessionLifeStyle.addAccount(sessionAccountVo);
    }

}
