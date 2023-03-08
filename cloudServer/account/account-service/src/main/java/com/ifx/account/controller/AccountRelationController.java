package com.ifx.account.controller;

import com.alibaba.fastjson2.JSON;
import com.ifx.account.route.accout.AccRelationRoute;
import com.ifx.account.service.reactive.ReactiveAccountRelationService;
import com.ifx.account.validator.ACCOUNTLOGIN;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * @author pengpeng
 * @description
 * @date 2023/2/10
 */
@RestController("accountRelation")
@RequestMapping(AccRelationRoute.ACCOUNT_RELATION_ROUTE)
@Slf4j
@Validated
public class AccountRelationController {

    @Autowired
    private ReactiveAccountRelationService relationService;





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
