package com.ifx.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableEurekaServer
public class GateWayApplication   {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GateWayApplication.class)
                .web(WebApplicationType.REACTIVE).run(args);
    }
}
