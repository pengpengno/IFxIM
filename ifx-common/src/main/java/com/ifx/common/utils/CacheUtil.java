package com.ifx.common.utils;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具
 */
public interface CacheUtil {

    public Boolean set(String  key,String value);

    public Boolean expire(String  key, String value, Long expireTime, TimeUnit timeUnit);

    public String getStr(String key);
    public Object get(String key);

}
