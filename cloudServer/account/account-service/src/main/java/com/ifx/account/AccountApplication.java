package com.ifx.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class})
@ComponentScan("com.ifx")
@EnableR2dbcRepositories(basePackages = "com.ifx")
//@EnableDiscoveryClient
@EnableWebFlux
//@EnableEurekaClient
//@EnableWebFluxSecurity
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
