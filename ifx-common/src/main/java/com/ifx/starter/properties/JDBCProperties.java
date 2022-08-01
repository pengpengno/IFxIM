package com.ifx.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "jdbc")
public class JDBCProperties {

    private String driver;

    private String username ;

    private String url;

    private String  password;
}
