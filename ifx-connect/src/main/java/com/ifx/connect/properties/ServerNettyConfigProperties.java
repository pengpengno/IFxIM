package com.ifx.connect.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ifx.connect.netty.server")
@Data
@Primary
public class ServerNettyConfigProperties {

    private Integer port = 8094;

    private String  host = "127.0.0.1";

    private Integer maxActiveSocket;

    private Integer coreActiveSocket;

    private Integer maxTimeOut;
}
