package com.ifx.account.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {


//    @Bean
//    public  DataSource druidSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
////        druidDataSource.setDriverClassName();
//        return druidDataSource;
//    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate( DataSource dataSource) throws Exception {
        SqlSessionTemplate sqlSessionTemplate=new SqlSessionTemplate(sqlSessionFactoryBean(dataSource).getObject(), ExecutorType.BATCH);
        return sqlSessionTemplate;
    }
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean (  DataSource dataSource){
        MybatisSqlSessionFactoryBean mpSession = new MybatisSqlSessionFactoryBean();
        mpSession.setDataSource(dataSource);
        return mpSession;
    }



}
