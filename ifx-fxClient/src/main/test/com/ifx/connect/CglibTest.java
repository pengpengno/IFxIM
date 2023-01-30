package com.ifx.connect;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.vo.session.SessionAccountVo;
import javafx.util.Callback;
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
        enhancer.setSuperclass(SessionAccountService.class);
        enhancer.setCallback(new IFxDubboProtocolProxy());
        SessionAccountService sessionService = (SessionAccountService) enhancer.create();
        Object o = sessionService.addAcc2Session(new SessionAccountVo());

//        Protocol protocol = sessionService.add0();
//        Object protocol2 = sessionService.addAcc(null);
//        log.info(" protocol = {} ",JSON.toJSONString(protocol));
//        log.info(" protocol = {} ",JSON.toJSONString(o));



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
    @Test
    public void testCglibProxy(){


    }

//  自定义生成  协议

    public static Protocol generateProtocol (Callback<Object,?> callback){
        Object call = callback.call(new IFxDubboProtocolProxy());
        return null;
    }

//    public static Protocol generateProtocol (Runnable runnable){
//
//        return null;
//
//    }
//    public static Protocol generateProtocol (Consumer runnable){
//
//        return null;
//
//    }


    public static void sendProtocol(Protocol protocol, TaskHandler taskHandler){

    }
}
