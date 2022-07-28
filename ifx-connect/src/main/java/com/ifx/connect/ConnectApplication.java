package com.ifx.connect;

import com.ifx.connect.properties.SocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.Resource;
import java.net.Socket;

@SpringBootApplication(exclude = DubboAutoConfiguration.class)
//@EnableDubboConfig
@Slf4j
public class ConnectApplication {

    @Resource
    private SocketProperties socketProperties;
    public static void main(String[] args) {
        SpringApplication.run(ConnectApplication.class, args);
//        log.info("sss{}",);
//        System.out.println(socketProperties.getPort());
    }
}
