package server.config;

import com.ifx.connect.netty.server.Server;
import com.ifx.connect.properties.SocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.netty.StartNettyServer;

@Configuration
@EnableConfigurationProperties(SocketProperties.class)
@Slf4j
public class ServerConfig {

    @Bean("netty")
    public Server applyNetty(SocketProperties socketProperties){
        StartNettyServer startNettyServer = new StartNettyServer();
        startNettyServer.startNetty(socketProperties.getPort());
        log.info("netty start succ on  port {} ",socketProperties.getPort());
        return startNettyServer;
    }
}
