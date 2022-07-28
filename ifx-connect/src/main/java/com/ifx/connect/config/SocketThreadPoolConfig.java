package com.ifx.connect.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class SocketThreadPoolConfig {
    @Bean("socketPool")
    public ExecutorService initSocketThreadPool(){
        return new ThreadPoolExecutor(100, 300, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(1024),
                new ThreadFactoryBuilder().setNamePrefix("socket-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }
}
