package com.ifx.connect.config;

import com.ifx.connect.connection.ServerConnect;
import com.ifx.connect.connection.SocketConnect;
import com.ifx.connect.properties.ServerNettyConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;

@Configuration
@EnableConfigurationProperties(ServerNettyConfigProperties.class)
public class SocketConfig {

    @Resource
    private ServerNettyConfigProperties serverNettyConfigProperties;

//    @Bean
//    public ServerConnect<ServerSocket> applySocketConnect() throws IOException {
//        ServerSocket serverSocket = new ServerSocket(serverNettyConfigProperties.getPort());
//        ServerConnect<ServerSocket> socketConnect = new SocketConnect();
//        return socketConnect;
//    }
    @Bean
    public ServerNettyConfigProperties apply(ServerNettyConfigProperties serverNettyConfigProperties){
        return serverNettyConfigProperties;
    }


}
