package com.ifx.account;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ifx.account.mapper")
@DubboComponentScan("com.ifx.account.service.impl")
@EnableDubbo
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
