package com.ifx.account.controller;

import com.alibaba.fastjson2.JSON;
import com.ifx.account.route.accout.AccRoute;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.validator.ACCOUNTLOGIN;
import com.ifx.account.vo.AccountAuthenticateVo;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * @author pengpeng
 * @description
 * @date 2023/2/10
 */
@RestController("account")
@RequestMapping(AccRoute.ACCOUNT_ROUTE)
@Slf4j
@Validated
public class AccountController {

    @Autowired
    private ReactiveAccountService accountService;


    @GetMapping(path = "/{account}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<AccountInfo> getAccountInfo(@PathVariable("account") String account){
        log.info("传入的 账户 {}",account);
//        EntityModel.of(accountService.findByAccount(account), Link.of())
        return accountService.findByAccount(account);
    }


    @PostMapping("/auth")
    public Mono<AccountAuthenticateVo> authenticateVoMono(@RequestBody @Validated(value = ACCOUNTLOGIN.class) AccountVo accountVo){
        return accountService.auth(accountVo);
    }



//    @PostMapping("/upload/file")
//    public Mono<ResponseEntity<Object>>convertFile( @RequestPart("file") FilePart filePart, @RequestPart(required = false,value = "meta") ConvertFileRequest request) {
//        return Mono.just(ResponseEntity.ok().build());
//    }


    @PostMapping("/jwt")
    public Mono<AccountInfo> jwtParse(@RequestParam String jwt){
        return accountService.parseJwt(jwt);
    }


    @PostMapping("/login")
    public Mono<AccountInfo> login(@RequestBody @Validated(value = ACCOUNTLOGIN.class) AccountVo accountVo){
        return accountService.login(accountVo);
    }


    @PostMapping(path = "/register")
    public Mono<AccountInfo> register(@RequestBody @Valid() AccountVo accountVo){
        log.info(" {} ", JSON.toJSONString(accountVo));
        return accountService.register(accountVo).log();
    }


}
