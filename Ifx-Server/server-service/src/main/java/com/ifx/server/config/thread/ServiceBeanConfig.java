package com.ifx.server.config.thread;

import com.ifx.server.service.ByteBufProcess;
import com.ifx.server.service.ByteBufProcessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfig {


    @Bean
    public ByteBufProcess byteBufProcess(){
        return ByteBufProcessService.getInstance();
    }
}
