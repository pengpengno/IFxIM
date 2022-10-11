package com.ifx.client.proxy;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.TaskHandler;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.service.ISessionAction;
import com.ifx.session.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodProxy;

import java.util.function.Function;

@Slf4j
public class ProxyBean {
    @SuppressWarnings(value = "all")
    public static <T> T getProxyBean(Class<T> tClass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
//        enhancer.setCallback(ClientApiProxy.getInstance());
//        enhancer.setMetho\
//        MethodProxy.create()
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return null;
            }
        });

//        new Function() {
//            @Override
//            public Object apply(Object o) {
//                return null;
//            }
//        }
//        enhancer.setCallback( () -> {
//            log.info("ssss");
//            return new ProxyBean();
//        });
//        enhancer.setCallbackFilter( () -> {
//            log.warn("s");
//        });
        return (T) enhancer.create();
    }

    public  static Protocol proxy(Function<Object,Protocol> function, TaskHandler taskHandler){
        return null;

//        function.apply()
    }

}
