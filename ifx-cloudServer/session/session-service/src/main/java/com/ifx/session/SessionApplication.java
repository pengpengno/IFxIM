package com.ifx.session;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan(basePackages = {"com.ifx.session.mapper"})
@DubboComponentScan("com.ifx.session")
@EnableDubbo
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class);
    }
}
