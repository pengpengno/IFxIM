package com.ifx.server;

import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class test {

    @DubboReference
    private AccountService accountService;

    @Test
    public void test (){
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount("wangpeng");
        accountBaseInfo.setPassword("111111");
        accountBaseInfo.setUserName("wangpeng");
        String register = accountService.register(accountBaseInfo);
        System.out.println(register);
    }

    @Test
    public void login (){
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount("wangpeng");
        accountBaseInfo.setPassword("111111");
//        accountBaseInfo.setUserName("wangpeng");
        Boolean login = accountService.login(accountBaseInfo);
        System.out.println(login);
    }
}
