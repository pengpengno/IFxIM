package com.ifx.server.config.thread;

import com.ifx.connect.process.ByteBufProcess;
import com.ifx.connect.process.ByteBufProcessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfig {

    @Bean
    public ByteBufProcess byteBufProcess(){
        return ByteBufProcessService.getInstance();
    }
}
