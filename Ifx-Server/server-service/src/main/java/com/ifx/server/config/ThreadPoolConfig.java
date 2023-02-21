package com.ifx.server.config;

import com.ifx.server.config.thread.ServerThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@Configuration
public class ThreadPoolConfig {

    @Bean("serverPool")
    public ExecutorService initSocketThreadPool(){
        return ServerThreadPool.getInstance().threadPool();
    }
}
