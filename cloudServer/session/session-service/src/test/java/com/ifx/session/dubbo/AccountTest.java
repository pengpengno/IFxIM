package com.ifx.session.dubbo;

import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.vo.AccountVo;
import com.ifx.session.SessionApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = {SessionApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AccountTest {

    private ReactiveAccountService accountService;
    @Test
    public  void listAllAccoutInfo(){
//        List<Map<String, Long>> test = accountService.listAllAccoutInfo();
//        List<AccountBaseInfo> accountBaseInfos = accountService.listAllAccoutInfo();
//        log.info(" {} ", JSON.toJSONString(test));
    }

    @Test
    public void register(){
        AccountVo accountVo = new AccountVo();
        accountVo.setAccount("pengpengon");
        accountVo.setPassword("myname");
//        String register = accountService.register(accountVo);
    }


}
