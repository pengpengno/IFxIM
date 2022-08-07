package com.ifx.client.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
    /**
     * theadPool  client  main business
     * @return
     */
    @Bean("clientPool")
    public ExecutorService initSocketThreadPool(){
        return new ThreadPoolExecutor(50, 300, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNamePrefix("client-business-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
