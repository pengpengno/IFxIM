package com.ifx.session.feign;

import cn.hutool.core.date.StopWatch;
import com.ifx.account.fegin.AccountApi;
import com.ifx.account.vo.AccountVo;
import com.ifx.session.SessionApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = {SessionApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AccountTest {
    @Autowired
    private AccountApi accountApi;

    @Test
    public void findByUserId(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("sent message");
        Long userId = 1630119513067073536L;
//        AccountInfo block = accountApi.getAccountInfo(userId).block();
//        AccountInfo block = accountApi.getAccountInfo(userId);
        stopWatch.stop();
//        log.info("time cost {}ms, user {}" , stopWatch.getLastTaskTimeMillis(),JSON.toJSONString(block));
    }

    @Test
    public void register(){
        AccountVo accountVo = new AccountVo();
        accountVo.setAccount("pengpengon");
        accountVo.setPassword("myname");
    }


}
