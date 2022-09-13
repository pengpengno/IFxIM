package com.ifx.client.config;

import com.ifx.client.connect.netty.NettyClient;
import com.ifx.connect.properties.ClientNettyConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Configuration
@EnableConfigurationProperties({ClientNettyConfigProperties.class})
@Slf4j
public class ClientConnectConfig {
    /** server host C2S /C2C */
//    private String host ;
    /** server  port  C2S /C2C   */
//    private Long port ;
//    @Resource
//    private ClientNettyConfigProperties configProperties;


//    @Bean
//    public NettyClient apply(@Autowired ClientNettyConfigProperties configProperties){
//        log.info("客户端netty 配置为  server host{}  port{}",
//                configProperties.getServerHost(),configProperties.getServerPort());
//        return  null;
//
//    }
}
