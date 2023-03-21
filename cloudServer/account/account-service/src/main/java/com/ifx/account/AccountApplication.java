package com.ifx.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = {"com.ifx","reactivefeign.spring.config"},exclude = {RedisRepositoriesAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class})
@EnableR2dbcRepositories(basePackages = "com.ifx")
@EnableWebFlux
@EnableFeignClients
//@EnableReactiveFeignClients
public class AccountApplication {


    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
