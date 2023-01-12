package com.ifx.account;

import com.ifx.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = {AccountApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AccountTest {

    @Resource
    private AccountService accountService;
    @Test
    public  void listAllAccoutInfo(){
//        List<Map<String, Long>> test = accountService.listAllAccoutInfo();
//        List<AccountBaseInfo> accountBaseInfos = accountService.listAllAccoutInfo();
//        log.info(" {} ", JSON.toJSONString(test));
    }
}
