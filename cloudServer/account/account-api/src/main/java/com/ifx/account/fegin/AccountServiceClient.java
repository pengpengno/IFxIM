package com.ifx.account.fegin;

import com.ifx.account.entity.Account;
import com.ifx.common.base.AccountInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
@FeignClient(name = "account",url = "${APIGATEWAY:http://localhost:8071/api/accout}")
public interface AccountServiceClient {

    @GetMapping(path = "/{account}")
//    @ResponseStatus(code = HttpStatus.OK)
    public Mono<AccountInfo> getAccountInfo(@PathVariable("account") String account){
        log.info("传入的 账户 {}",account);
//        EntityModel.of(accountService.findByAccount(account), Link.of())
        return accountService.findByAccount(account);
    }

}
