package com.ifx.account.fegin;

import com.ifx.common.base.AccountInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
@FeignClient(name = "account",url = "${APIGATEWAY:http://localhost:8671/api/accout}")
public interface AccountServiceClient {

    @GetMapping(path = "/{account}")
    public Mono<AccountInfo> getAccountInfo(@PathVariable("account") String account);



}
