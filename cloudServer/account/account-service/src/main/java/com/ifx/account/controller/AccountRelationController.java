package com.ifx.account.controller;

import com.alibaba.fastjson2.JSON;
import com.ifx.account.route.accout.AccRoute;
import com.ifx.account.service.reactive.ReactiveAccountRelationService;
import com.ifx.account.validator.ACCOUNTLOGIN;
import com.ifx.account.vo.AccountRelationVo;
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
@RequestMapping(AccRoute.ACCOUNT_RELATION_ROUTE)
@Slf4j
@Validated
public class AccountRelationController {

    @Autowired
    private ReactiveAccountRelationService relationService;


    @GetMapping(path = "/{account}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<AccountRelationVo> getAccountInfo(@PathVariable("account") String account){
        log.info("传入的 账户 {}",account);
//        EntityModel.of(accountService.findByAccount(account), Link.of())
        return relationService.listRelationWithAccount(account);
    }


    @GetMapping(path = "/acc")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<AccountInfo> test( @RequestParam(required = false) @NotNull(message = "account not be null") String account){
        log.info("传入的 账户 {}",account);
//        EntityModel.of(accountService.findByAccount(account), Link.of())
        return null;
    }



    @RequestMapping(method = RequestMethod.POST,path = "/acc")
    public Mono<ResponseEntity<Acc>> testAcc(@RequestBody @Valid Acc acc){
//        Set<javax.validation.ConstraintViolation<Acc>> constraintViolations = ValidatorUtil.validateOne(acc);
//        log.info("sss {}" , JSONObject.toJSONString(constraintViolations));
//        return Mono.just(ResponseEntity.ok(acc)).onErrorResume(WebExchangeBindException.class,e-> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
        return null;
    }



    @PostMapping("/login")
    public Mono<AccountInfo> login(@RequestBody @Validated(value = ACCOUNTLOGIN.class) AccountVo accountVo){
        return null;
    }


//    @PostMapping
    @PostMapping(path = "/register")
    public Mono<AccountInfo> register(@RequestBody @Valid() AccountVo accountVo){
        log.info(" {} ", JSON.toJSONString(accountVo));
//        return accountService.register(accountVo).log();
        return null;
    }


}
