package com.ifx.client;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用此启动类启动
 * @author pengpeng
 * @description
 * @date 2023/2/23
 */
@SpringBootApplication(scanBasePackages = {"com.ifx","cn.hutool.extra.spring"})
@Slf4j
public class APP implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("spring start up  success");
    }

    public static void main(String[] args) {
        SpringApplication.run(APP.class);
        Application.launch(ClientApplication.class);
    }
}
