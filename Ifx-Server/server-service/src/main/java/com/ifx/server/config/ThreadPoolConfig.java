package com.ifx.server.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean("serverPool")
    public ExecutorService initSocketThreadPool(){
        return new ThreadPoolExecutor(150, 300, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNamePrefix("server-business-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
