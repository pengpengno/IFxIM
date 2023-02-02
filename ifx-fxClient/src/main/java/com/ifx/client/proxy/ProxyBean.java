package com.ifx.client.proxy;

import com.ifx.client.util.IdUtil;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import com.ifx.session.service.SessionAccountService;
import com.ifx.session.vo.session.SessionAccountVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

import javax.annotation.Resource;
import java.util.function.Function;


/***
 * 代理实现类
 */
@Slf4j
@Deprecated
public class ProxyBean {

    @Resource
    private SessionAccountService sessionAccountService;
    @SuppressWarnings(value = "all")
    public static <T> T getProxyBean(Class<T> tClass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return null;
            }
        });

        return (T) enhancer.create();
    }

    public  static Protocol proxy(Function<Object,Object> function, TaskHandler taskHandler){
        String traceId = IdUtil.traceId();
        ClientToolkit.getDefaultClientAction();
        return null;

    }


    public  void s(){
        proxy((k)->sessionAccountService.addAcc2Session(new SessionAccountVo()), Protocol::getResult);
    }

}
