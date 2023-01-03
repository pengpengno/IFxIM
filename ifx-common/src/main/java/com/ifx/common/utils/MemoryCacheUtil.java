package com.ifx.common.utils;

import cn.hutool.cache.impl.LRUCache;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

//@Component("Memory")
@Slf4j
public class MemoryCacheUtil implements CacheUtil{

    private LRUCache<String,Object> lruCache;

    private static Integer cacheCapacity = 1000;

    public void initCache(){
        if (lruCache == null){
            lruCache = new LRUCache<>(cacheCapacity);
        }
    }

    @Override
    public String getStr(String key) {
        return null;
    }

    @Override
    public Boolean set(String key, Object value) {
        initCache();
        lruCache.put(key,value);
        return Boolean.TRUE;
    }

//    @Override
    public Boolean expire(String key, String value, Long expireTime, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public Boolean expire(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        return null;
    }


    @Override
    public Object get(String key) {
        Object value = lruCache.get(key);
        return value;
    }
}
