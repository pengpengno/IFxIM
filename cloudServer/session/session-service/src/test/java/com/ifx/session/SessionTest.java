package com.ifx.session;

import com.ifx.session.service.SessionAccountService;
import com.ifx.session.service.SessionService;
import com.ifx.session.vo.session.SessionInfoVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
//        List<SessionAccount> list = sessionAccountService.list();
//        log.info(JSON.toJSONString(list));

    }
    
    @Test
    public void newSession(){
        SessionInfoVo sessionInfoVo = new SessionInfoVo();
        sessionInfoVo.setSessionName("测试会话");
//        Long aLong = sessionService.addorUpSession(sessionInfoVo);

//        log.info(JSON.toJSONString(aLong));
//        System.out.println(aLong);
    }
}
