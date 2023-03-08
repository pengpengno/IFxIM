package com.ifx.client.proxy;

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/***
 * 客户端接口拦截代理
 * @author pengpeng
 */
@Slf4j
@Singleton
public class ClientApiProxy implements MethodInterceptor {



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
            log.debug(" load apiProxy  prev");
//            Protocol protocol = DubboGenericParse.applyMsgProtocol( method, args);
//            protocol.setType(IFxMsgProtocol.CLIENT_TO_SERVER_MSG_HEADER);
//            String serverTrace = IdUtil.traceId();
//            protocol.setServerTrace(serverTrace);
//            ClientToolkit.getDefaultClientAction().sendJsonMsg(protocol);
//            MDC.put(CommonConstant.SERVER_TRACE,serverTrace);
//            log.debug(" load apiProxy  prev server trace {}",serverTrace);
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
