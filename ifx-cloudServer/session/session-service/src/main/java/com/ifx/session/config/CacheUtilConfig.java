package com.ifx.session.config;

import com.ifx.common.utils.CacheUtil;
import com.ifx.common.utils.MemoryCacheUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CacheUtilConfig {

    @Bean(name = "Memory")
    public CacheUtil cacheUtil()  {
        return new MemoryCacheUtil();
    }
}
