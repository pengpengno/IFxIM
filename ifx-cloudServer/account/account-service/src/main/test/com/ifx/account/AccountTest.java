package com.ifx.account;

import com.ifx.account.entity.Account;
import com.ifx.account.mapstruct.AccountHelper;
import com.ifx.account.service.reactive.ReactiveAccountService;
import com.ifx.common.base.AccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {AccountApplication.class})
//@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AccountTest {

//    @Resource
    private ReactiveAccountService accountService;
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
        AccountInfo accountInfo =AccountInfo.builder().build();
        account.setAccount("sadsadas");
        //when
        accountInfo = AccountHelper.INSTANCE.buildAccountInfo(account );

        //then
        Assertions.assertEquals(accountInfo.getAccount(),account.getAccount());
    }
}
