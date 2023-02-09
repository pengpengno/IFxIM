package com.ifx.client.ann.proxy;

import com.ifx.client.proxy.ClientApiProxy;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/30
 */
public class ProxyUtil {

    /***
     * 生成地理类服务
     * @param tClass
     * @return
     * @param <T>
     */
    public  static <T> T generateService(Class<T> tClass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
        enhancer.setCallback( ClientApiProxy.getInstance());
        return (T) enhancer.create();
    }

}
