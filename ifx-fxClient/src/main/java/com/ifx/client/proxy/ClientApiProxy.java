package com.ifx.client.proxy;

import cn.hutool.extra.spring.SpringUtil;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.proto.ifx.IFxMsgProtocol;
import com.ifx.connect.proto.parse.DubboGenericParse;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Method;
import java.util.Arrays;

/***
 * 客户端接口拦截代理
 * @author pengpeng
 */
@Slf4j
public class ClientApiProxy implements MethodInterceptor {


    public void initialize(ConfigurableApplicationContext applicationContext) {
        Arrays.stream(this.getClass().getFields()).forEach(e->SpringUtil.getBean(e.getClass()));
    }
    /***
     * 此处用以代理实现客户端api调用
     * @param obj "this", the enhanced object
     * @param method intercepted Method
     * @param args argument array; primitive types are wrapped
     * @param proxy used to invoke super (non-intercepted method); may be called
     * as many times as needed
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        try{
            log.debug(" load apiProxy  prev");
//            Method getFastClass = ReflectUtil.getMethod(proxy.getClass(), "getFastClass");
//            getFastClass.setAccessible(true);
//            FastClass invoke = ReflectUtil.invoke(proxy, getFastClass);
            Protocol protocol = DubboGenericParse.applyMsgProtocol( method, args);
            protocol.setType(IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER);
            ClientToolkit.getDefaultClientAction().sendJsonMsg(protocol);
            log.debug(" load apiProxy  prev");
        return null;
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
