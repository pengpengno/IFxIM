package com.ifx.common.utils;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具
 */
public interface CacheUtil {

    public Boolean set(String  key,Object value);


    public Boolean expire(String  key, Object value, Long expireTime, TimeUnit timeUnit);

    public default String getStr(String key){
        Object res = get(key);
        return res == null ? null : res.toString();
    }

    public Object get(String key);

    default <T>  T get(String key,Class<T> tclass) throws ClassCastException{
        return null;
    }

}
