package com.ifx.connect.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ifx.connect.netty.client")
@Data
public class ClientNettyConfigProperties {

    private String serverHost;

    private Integer serverPort;

    private Integer connectTimeOut;

    private Integer retryTimes;

    private Boolean autoReConnect = Boolean.TRUE;
}
