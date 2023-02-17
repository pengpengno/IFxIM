package com.ifx.session.dubbo;

import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.session.SessionApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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

    @Test
    public void testMapperInfo(){
        //given
        Account account = new Account(  );
        AccountInfo accountInfo = new AccountInfo();
        account.setAccount("sadsadas");
        //when
        accountInfo = AccountHelper.INSTANCE.buildAccountInfo(account );

        //then
        Assert.assertEquals(accountInfo.getAccount(),account.getAccount());
    }
}
