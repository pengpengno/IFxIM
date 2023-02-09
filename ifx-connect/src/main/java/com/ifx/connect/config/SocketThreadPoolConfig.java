package com.ifx.connect.config;

import com.ifx.connect.config.thread.ClientThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@Configuration
public class SocketThreadPoolConfig {
    @Bean("socketPool")
    public ExecutorService initSocketThreadPool(){
        return ClientThreadPool.getInstance().threadPool();
    }



}
