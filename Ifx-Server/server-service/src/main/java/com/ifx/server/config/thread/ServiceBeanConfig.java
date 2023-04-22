package com.ifx.server.config.thread;


import com.ifx.connect.spi.netty.ByteBufProcess;
import com.ifx.connect.spi.netty.ByteBufProcessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfig {

    @Bean
    public ByteBufProcess byteBufProcess(){
        return ByteBufProcessService.getInstance();
    }
}
