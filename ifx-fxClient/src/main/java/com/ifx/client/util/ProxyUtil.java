package com.ifx.client.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.ifx.client.app.pane.SearchPane;
import com.ifx.client.proxy.ProxyBean;
import com.ifx.common.ann.client.Proxy;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;


/***
 * 代理工具类
 */
public class ProxyUtil {

    public static void proxy(Object o ) {
        Arrays.stream(SearchPane.AccountMiniPane.class.getFields())
                .filter(e-> ObjectUtil.isNotNull(e.getAnnotation(Proxy.class)))
                .forEach(k-> {
                    k.setAccessible(true);
                    Object proxyBean = ProxyBean.getProxyBean(k.getType());
                    ReflectUtil.setFieldValue(o,k, proxyBean);
                });
    }

    private static ConcurrentHashMap<Class<?>,Object> proxyMap ;


    /***
     * 获取代理实体
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> tClass){
        initProxyMap();
        return (T)proxyMap.computeIfAbsent(tClass,(k)->ProxyBean.getProxyBean(tClass));
    }



    public static void initProxyMap(){
        if (proxyMap == null){
            proxyMap = new ConcurrentHashMap<>();
        }
    }

    private enum INSTANCE{
        INSTANCE;
        public final ProxyUtil instance ;
        INSTANCE(){
            instance = new ProxyUtil();
        }
        public static ProxyUtil getInstance(){
            return INSTANCE.instance;
        }
    }
    public static ProxyUtil getInstance(){
        return ProxyUtil.INSTANCE.getInstance();
    }



}
