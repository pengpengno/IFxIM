package com.ifx.connect.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ifx.connect.netty.server")
@Data
public class ServerNettyConfigProperties {

    private Integer port;

    private String  host;

    private Integer maxActiveSocket;

    private Integer coreActiveSocket;

    private Integer maxTimeOut;
}
