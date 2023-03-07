package com.ifx.account;

import com.ifx.account.service.impl.reactive.ReactiveAccountServiceImpl;
import com.ifx.account.service.impl.reactive.Temp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = {"com.ifx"},exclude = {RedisRepositoriesAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class})
@EnableR2dbcRepositories(basePackages = "com.ifx")
@EnableWebFlux
public class AccountApplication implements CommandLineRunner {
    @Autowired
    ReactiveAccountServiceImpl accountService;

    @Autowired
    Temp temp;


    @Override
    public void run(String... args) throws Exception {

        accountService.findByAccount("peng");
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
