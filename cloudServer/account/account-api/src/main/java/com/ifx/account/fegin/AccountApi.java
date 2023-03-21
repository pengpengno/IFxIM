package com.ifx.account.fegin;

import com.ifx.account.route.accout.AccRoute;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/21
 */
//@ReactiveFeignClient(path = AccRoute.ACCOUNT_ROUTE,name = "account-api",url = "${account.service.url:localhost:8001}")
@FeignClient(path = AccRoute.ACCOUNT_ROUTE,name = "account-api",url = "${account.service.url:localhost:8001}")
public interface AccountApi {

    @PostMapping(path = "/register")
//    Mono<AccountInfo> register(@RequestBody @Valid() AccountVo accountVo);
    AccountInfo register(@RequestBody @Valid() AccountVo accountVo);


    @GetMapping(path = "/accountInfo")
    @ResponseStatus(code = HttpStatus.OK)
    AccountInfo getAccountInfo(@RequestParam("userId") Long userId);
//    public Mono<AccountInfo> getAccountInfo(@RequestParam("userId") Long userId);


}
