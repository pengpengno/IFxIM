package com.ifx.guice;

import com.ifx.account.service.AccountService;
import com.ifx.client.ann.ProxyService;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/5
 */
public class NamedService {
    @ProxyService
    private AccountService accountService;



}
