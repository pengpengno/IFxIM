package com.ifx.session.service;

import com.ifx.account.fegin.AccountServiceClient;
import com.ifx.common.base.AccountInfo;
import com.ifx.session.SessionApplication;
import com.ifx.session.vo.session.SessionInfoVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = SessionApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class SessionTest {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionAccountService sessionAccountService;
    
    @Autowired
    private AccountServiceClient accountServiceClient;


    @Test
    public void listSessionAcc(){
        Long sessionId = 1l;
//        List<SessionAccount> list = sessionAccountService.list();
//        log.info(JSON.toJSONString(list));

    }
    @Test
    public void listAccount (){
        Mono<AccountInfo> pengpeng = accountServiceClient.getAccountInfo("pengpeng");
        System.out.println(pengpeng.block());
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
