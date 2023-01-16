package com.ifx.account;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan
@DubboComponentScan("com.ifx.service.impl")
@EnableDubbo
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
