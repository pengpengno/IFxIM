package com.ix.gateway;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan
public class GateWayApplication   {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);

    }
}
