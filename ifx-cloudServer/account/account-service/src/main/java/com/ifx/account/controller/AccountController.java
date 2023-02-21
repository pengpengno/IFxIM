package com.ifx.account.controller;

import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.validator.ACCOUNTLOGIN;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * @author pengpeng
 * @description
 * @date 2023/2/10
 */
@RestController("account")
@RequestMapping("/api/account")
@Slf4j
//@Valid
@Validated
public class AccountController {

    @Autowired
    private ReactiveAccountService accountService;


    @GetMapping(path = "/{account}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<AccountInfo> getAccountInfo(@PathVariable("account") String account){
        log.info("传入的 账户 {}",account);
//        EntityModel.of(accountService.findByAccount(account), Link.of())
        return accountService.findByAccount(account).log();
    }


    @GetMapping(path = "/acc")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<AccountInfo> test( @RequestParam(required = false) @NotNull(message = "account not be null") String account){
        log.info("传入的 账户 {}",account);
//        EntityModel.of(accountService.findByAccount(account), Link.of())
        return accountService.findByAccount(account).log();
    }



    @RequestMapping(method = RequestMethod.POST,path = "/acc")
    public Mono<ResponseEntity<Acc>> testAcc(@RequestBody @Valid Acc acc){
//        Set<javax.validation.ConstraintViolation<Acc>> constraintViolations = ValidatorUtil.validateOne(acc);
//        log.info("sss {}" , JSONObject.toJSONString(constraintViolations));
//        return Mono.just(ResponseEntity.ok(acc)).onErrorResume(WebExchangeBindException.class,e-> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
        return null;
    }



    @PostMapping("/login")
    public Mono<AccountInfo> login(@RequestBody(required = false) @Validated(value = ACCOUNTLOGIN.class) AccountVo accountVo){
        return accountService.login(accountVo);
    }


//    @PostMapping
    @PutMapping(path = "/{account}")
    public Mono<AccountInfo> register(@RequestBody @Validated AccountVo accountVo){
        return accountService.register(accountVo);
    }


}
