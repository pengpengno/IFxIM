package com.ifx.session.config;

import com.ifx.common.utils.CacheUtil;
import com.ifx.common.utils.MemoryCacheUtil;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CacheUtilConfig {

    @Bean(name = "Memory")
    public CacheUtil cacheUtil()  {
        return new MemoryCacheUtil();
    }


    @Bean
    public Decoder feignDecoder() {

        ObjectFactory<HttpMessageConverters> messageConverters = () -> {
            HttpMessageConverters converters = new HttpMessageConverters();
            return converters;
        };
        return new SpringDecoder(messageConverters);
    }
}
