package com.ifx.account.service.reactive;

import com.ifx.account.vo.AccountAuthenticateVo;
import com.ifx.account.vo.AccountVo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.common.base.AccountInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */
public interface ReactiveAccountService {


    Mono<AccountInfo> findByAccount(String account);

    Mono<AccountInfo> findByUserId(Long userId);

    /***
     * 根据账户搜索 Vo
     * @param accountSearchVo 账户信息
     * @return 账户信息
     */
    Flux<AccountInfo> findBySearch(AccountSearchVo accountSearchVo);

    Flux<AccountInfo> findByUserIds(Set<Long> userId);


    Mono<AccountInfo> login(AccountVo accountVo);


    Mono<AccountAuthenticateVo> auth(AccountVo accountVo);

    Mono<AccountInfo> parseJwt(String jwt);

    Mono<AccountInfo> register(AccountVo accountVo);





}
