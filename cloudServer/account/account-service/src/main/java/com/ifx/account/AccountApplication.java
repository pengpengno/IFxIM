package com.ifx.account;

import com.ifx.account.service.impl.reactive.ReactiveAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = {"com.ifx"},exclude = {RedisRepositoriesAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class})
@EnableR2dbcRepositories(basePackages = "com.ifx")
@EnableWebFlux
public class AccountApplication {
    @Autowired
    ReactiveAccountServiceImpl accountService;



    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
