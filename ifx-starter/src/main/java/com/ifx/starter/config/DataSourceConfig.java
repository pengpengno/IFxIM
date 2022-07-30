package com.ifx.starter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ifx.starter.properties.JDBCProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JDBCProperties.class)
public class DataSourceConfig {
    @Bean("druidSource")
    public DruidDataSource applyDruid(JDBCProperties jdbcProperties){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(jdbcProperties.getDriver());
        druidDataSource.setUrl(jdbcProperties.getUrl());
        druidDataSource.setPassword(jdbcProperties.getPassword());
        druidDataSource.setUsername(jdbcProperties.getUsername());
        return druidDataSource;
    }
}
