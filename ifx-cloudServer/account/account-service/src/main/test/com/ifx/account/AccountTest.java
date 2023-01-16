package com.ifx.account;

import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.account.service.AccountService;
import com.ifx.common.base.AccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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

    @Test
    public void testMapperInfo(){
        //given
        Account account = new Account(  );
        AccountInfo accountInfo = new AccountInfo();
        account.setAccount("sadsadas");
        //when
        accountInfo = AccountHelper.INSTANCE.trans2Info(account );

        //then
        Assert.assertEquals(accountInfo.getAccount(),account.getAccount());
    }
}
