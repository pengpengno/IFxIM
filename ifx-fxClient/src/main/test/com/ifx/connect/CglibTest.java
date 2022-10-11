package com.ifx.connect;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.client.service.SessionActionService;
import com.ifx.connect.proto.Protocol;
import com.ifx.session.entity.Session;
import com.ifx.session.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
@Slf4j

public class CglibTest {
    @Test
    public void cglibTest(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SessionActionService.class);
        enhancer.setCallback(new Proxy());
        SessionActionService sessionService = (SessionActionService) enhancer.create();
        Protocol protocol = sessionService.add0();
        log.info(" protocol = {} ",JSON.toJSONString(protocol));

    }
    
    @Test
    public void testSerial(){
        ArrayList<Integer> numbers = CollectionUtil.newArrayList(11111, 2, 22, 2, 3, 3 );
        String jsonString = JSON.toJSONString(numbers);
        Object o = JSON.parseObject(jsonString, Object.class);
        List<Integer> parse2 = (List<Integer>) o;
        Class<?> aClass = o.getClass();
        List<Integer> parse = JSON.parseArray(jsonString,Integer.class);
    }
}
