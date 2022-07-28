package com.ifx.connect.config;

import com.ifx.connect.properties.SocketProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableConfigurationProperties(SocketProperties.class)
public class SocketConfig {



}
