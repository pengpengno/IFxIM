package com.ifx.account.stream.test;

import com.ifx.account.AccountApplication;
import com.ifx.account.service.TestReactorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/8
 */

@SpringBootTest(classes = {AccountApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ReactorDubboTest {


    TestReactorService testReactorService;
    @Test
    public void test(){

        testReactorService.getMonoLong(9999l).map(k->k.toString()).doOnNext(log::info).subscribe();
        testReactorService.getMonoLong(null).map(k->k.toString()).doOnNext(log::info).subscribe();
    }
}
