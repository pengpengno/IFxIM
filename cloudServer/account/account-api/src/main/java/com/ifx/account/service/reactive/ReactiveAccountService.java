package com.ifx.account.service.reactive;

import com.ifx.account.vo.AccountAuthenticateVo;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/14
 */
public interface ReactiveAccountService {


    Mono<AccountInfo> findByAccount(String account);

    Mono<AccountInfo> findByUserId(Long userId);

    Flux<AccountInfo> findByUserIds(Iterable<Long> userId);


    Mono<AccountInfo> login(AccountVo accountVo);


    Mono<AccountAuthenticateVo> auth(AccountVo accountVo);

    Mono<AccountInfo> parseJwt(String jwt);

    Mono<AccountInfo> register(AccountVo accountVo);





}
