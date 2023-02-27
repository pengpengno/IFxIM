package com.ifx.session.dubbo;

import com.ifx.session.SessionApplication;
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

@SpringBootTest(classes = {SessionApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ReactorDubboTest {

//    @DubboReference(scope = "local")
//    @DubboReference
//    TestReactorService testReactorService;


    @Test
    public void test(){
//        testReactorService.getMonoLong(9999l).map(k->k.toString()).doOnNext(log::info).subscribe();
//        testReactorService.getMonoLong(null).map(k->k.toString()).doOnNext(log::info).subscribe();
    }
}
