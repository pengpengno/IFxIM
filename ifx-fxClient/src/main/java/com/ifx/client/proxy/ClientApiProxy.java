package com.ifx.client.proxy;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.client.service.ClientService;
import com.ifx.connect.connection.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import com.ifx.connect.proto.dubbo.DubboProtocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ClientApiProxy implements MethodInterceptor , ApplicationListener<ContextRefreshedEvent> {


    @Resource
    private  ClientAction clientAction;

    @Resource
    private  ClientService clientService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Arrays.stream(this.getClass().getFields()).forEach(e->SpringUtil.getBean(e.getClass()));
    }

    public void initialize(ConfigurableApplicationContext applicationContext) {
        Arrays.stream(this.getClass().getFields()).forEach(e->SpringUtil.getBean(e.getClass()));
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        try{
            log.info(" load apiProxy  prev");
            Method getFastClass = ReflectUtil.getMethod(proxy.getClass(), "getFastClass");
            getFastClass.setAccessible(true);
            FastClass invoke = ReflectUtil.invoke(proxy, getFastClass);

            DubboApiMetaData metaData = DubboGenericParse.applyMeta0(invoke.getJavaClass(), method, args);
            Protocol protocol = new DubboProtocol();
            protocol.setProtocolBody(JSON.toJSONString(metaData));
            protocol.setType(IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER);
            clientAction.sendJsonMsg(protocol);
            log.info(" load apiProxy  prev");
            return null;
        }
        catch(Exception e){
            log.error(" {} ", ExceptionUtil.stacktraceToString(e));
        }
        return null;


    }







    private ClientApiProxy(){

    }

    private enum INSTANCE{
        INSTANCE;
        public final ClientApiProxy instance ;
        INSTANCE(){
            instance = new ClientApiProxy();
        }
        public static ClientApiProxy getInstance(){
            return INSTANCE.instance;
        }
    }
    public static ClientApiProxy getInstance(){
        return ClientApiProxy.INSTANCE.getInstance();
    }


}
