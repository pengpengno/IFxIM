package com.ifx.account;

import com.alibaba.fastjson2.JSON;
import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.account.repository.AccountRepository;
import com.ifx.common.base.AccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;

@SpringBootTest(value = "application.yaml" ,classes = {AccountApplication.class})
//@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AccountTest {


    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    public void r2dbcEntity(){
//        new
        Mono<Account> pengpeng = accountRepository.findByAccount1("pengpeng");
        pengpeng.doOnNext(s-> log.info(JSON.toJSONString(s))).subscribe();
//        r2dbcEntityTemplate.select()
    }
    @Test
    public void testMapperInfo(){
        //given
        Account account = new Account(  );
        AccountInfo accountInfo =AccountInfo.builder().build();
        account.setAccount("sadsadas");
        //when
        accountInfo = AccountHelper.INSTANCE.buildAccountInfo(account );

        //then
        Assertions.assertEquals(accountInfo.getAccount(),account.getAccount());
    }
}
