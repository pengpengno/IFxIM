package com.ifx.account;

import com.alibaba.fastjson.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {AccountApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AccountTest {

    @Resource
    private AccountService accountService;
    @Test
    public  void st(){
        List<Map<String, Long>> test = accountService.test(1l);
        List<AccountBaseInfo> accountBaseInfos = accountService.listAllAccoutInfo();
        log.info(" {} ", JSON.toJSONString(test));
    }
}
