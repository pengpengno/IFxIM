package com.ifx.client;

import com.ifx.connect.connection.client.ClientLifeStyle;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class APP  implements CommandLineRunner {


    @Autowired
    ClientLifeStyle clientLifeStyle;

    @Autowired
    ClientNettyConfigProperties configProperties;
    @Override
    public void run(String... args) throws Exception {
        try{
            clientLifeStyle.connect();
        }catch (Exception exception){
            log.error("remote server  connect fail ");
            clientLifeStyle.reTryConnect();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(APP.class);
        Application.launch(ClientApplication.class);
    }
}
