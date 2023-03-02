package com.ifx.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.ifx")
@EnableFeignClients
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class);
    }
}
