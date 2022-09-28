package com.ifx.session;

import com.alibaba.fastjson2.JSON;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class SessionTest {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionAccountService sessionAccountService;
    
    
    @Test
    public void listSessionAcc(){
        Long sessionId = 1l;
        List<SessionAccount> list = sessionAccountService.list();
        log.info(JSON.toJSONString(list));

    }
    
    @Test
    public void newSession(){
        Long aLong = sessionService.newSession();

        log.info(JSON.toJSONString(aLong));
        System.out.println(aLong);
    }
}
