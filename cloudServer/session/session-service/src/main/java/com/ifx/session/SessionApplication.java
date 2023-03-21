package com.ifx.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = {"com.ifx","reactivefeign.spring.config"})
@EnableFeignClients(basePackages = {"com.ifx"})
//@EnableReactiveFeignClients(basePackages = {"com.ifx"})
@EnableWebFlux
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class);
    }
}
