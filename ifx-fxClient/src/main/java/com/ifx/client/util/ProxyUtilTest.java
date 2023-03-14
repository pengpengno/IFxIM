package com.ifx.client.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.ifx.client.app.pane.SearchPane;
import com.ifx.common.ann.client.Proxy;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;


/***
 * 代理工具类
 */
public class ProxyUtilTest {


    private static ConcurrentHashMap<Class<?>,Object> proxyMap ;


    public static void initProxyMap(){
        if (proxyMap == null){
            proxyMap = new ConcurrentHashMap<>();
        }
    }

    private enum INSTANCE{
        INSTANCE;
        public final ProxyUtilTest instance ;
        INSTANCE(){
            instance = new ProxyUtilTest();
        }
        public static ProxyUtilTest getInstance(){
            return INSTANCE.instance;
        }
    }
    public static ProxyUtilTest getInstance(){
        return ProxyUtilTest.INSTANCE.getInstance();
    }



}
