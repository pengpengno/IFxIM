package com.ifx.session;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.ifx.session.mapper"})
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class);
    }
}
