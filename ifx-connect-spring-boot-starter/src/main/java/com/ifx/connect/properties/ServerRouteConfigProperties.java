package com.ifx.connect.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ifx.api.gateway")
@Data
@Primary
public class ServerRouteConfigProperties {

    private String host;

    private Integer port;

    private Integer retryTimes;

    private Integer timeOut;
}
