//package com.ifx.gateway.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.ifx.starter.properties.JDBCProperties;
//import org.apache.ibatis.session.ExecutorType;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableConfigurationProperties(JDBCProperties.class)
//public class DataSourceConfig {
//    @Bean("druidSource")
//    public DruidDataSource applyDruid(JDBCProperties jdbcProperties){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(jdbcProperties.getDriver());
//        druidDataSource.setUrl(jdbcProperties.getUrl());
//        druidDataSource.setPassword(jdbcProperties.getPassword());
//        druidDataSource.setUsername(jdbcProperties.getUsername());
//        return druidDataSource;
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate( @Autowired @Qualifier("druidSource") DataSource dataSource) throws Exception {
//        SqlSessionTemplate sqlSessionTemplate=new SqlSessionTemplate(sqlSessionFactoryBean(dataSource).getObject(), ExecutorType.BATCH);
//        return sqlSessionTemplate;
//    }
//    @Bean
//    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean ( @Autowired @Qualifier("druidSource") DataSource dataSource){
//        MybatisSqlSessionFactoryBean mpSession = new MybatisSqlSessionFactoryBean();
//        mpSession.setDataSource(dataSource);
//        return mpSession;
//    }
//
//
//
//}
