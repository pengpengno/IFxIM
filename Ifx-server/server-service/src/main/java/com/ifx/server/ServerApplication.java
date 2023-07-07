package com.ifx.server;

import com.ifx.connect.connection.server.ReactiveServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ifx")
@Slf4j
public class ServerApplication implements CommandLineRunner {


    @Autowired
    private ReactiveServer server;


    public void run(String... args)  {
       server.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}
