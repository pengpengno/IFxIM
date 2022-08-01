package com.ifx.connect;

import com.ifx.connect.properties.SocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.Resource;

@SpringBootApplication
@Slf4j
public class ConnectApplication {

    @Resource
    private SocketProperties socketProperties;
    public static void main(String[] args) {
        SpringApplication.run(ConnectApplication.class, args);
    }
}
