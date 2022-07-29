package com.ifx.connect.config;

import com.ifx.connect.connection.ServerConnect;
import com.ifx.connect.connection.SocketConnect;
import com.ifx.connect.properties.SocketProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;

@Configuration
@EnableConfigurationProperties(SocketProperties.class)
public class SocketConfig {

    @Resource
    private SocketProperties socketProperties;

    @Bean
    public ServerConnect<ServerSocket> applySocketConnect() throws IOException {
        ServerSocket serverSocket = new ServerSocket(socketProperties.getPort());
        ServerConnect<ServerSocket> socketConnect = new SocketConnect();
        return socketConnect;
    }


}
