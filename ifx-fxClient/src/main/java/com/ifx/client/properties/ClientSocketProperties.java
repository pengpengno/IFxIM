package com.ifx.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ifx.connect.server.port")
@Data
public class ClientSocketProperties {

    private String serverHost;

    private Long port;

}
