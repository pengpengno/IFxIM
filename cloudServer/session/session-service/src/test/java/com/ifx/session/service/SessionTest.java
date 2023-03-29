package com.ifx.session.service;

import com.ifx.account.fegin.AccountApi;
import com.ifx.session.SessionApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SessionApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class SessionTest {

    @Resource
    private SessionService sessionService;
    @Resource
    private SessionAccountService sessionAccountService;
    
    @Autowired
    private AccountApi accountApi;



}
