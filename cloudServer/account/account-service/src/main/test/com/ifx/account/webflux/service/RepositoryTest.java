package com.ifx.account.webflux.service;

import com.ifx.account.AccountApplication;
import com.ifx.account.entity.Account;
import com.ifx.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/24
 */
@SpringBootTest(classes = {AccountApplication.class})
@Slf4j
public class RepositoryTest {
    
    
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private AccountRepository accountRepository;
    
    
    @Test
    public void r2dbcEntity(){
//        new
        Mono<Account> pengpeng = accountRepository.findByAccount("pengpeng");
        StepVerifier.create(pengpeng.hasElement())
                        .expectNext(Boolean.TRUE);
        Assert.assertTrue(pengpeng.hasElement().block());
    }
}
