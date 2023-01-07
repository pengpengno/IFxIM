package com.ifx.gateway;


import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
//import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class test {

//    @DubboReference
    private AccountService accountService;

    @Test
    public void test (){
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount("ewqewqeqwe");
        accountBaseInfo.setPassword("111111");
        accountBaseInfo.setUserName("wangpeng");
        String register = accountService.register(accountBaseInfo);
        System.out.println(register);
    }
}
