package com.ifx.connect.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ifx.connect.socket")
@Data
public class SocketProperties {

    private Integer port;

    private String  host;

    private Integer maxActiveSocket;

    private Integer coreActiveSocket;
}
