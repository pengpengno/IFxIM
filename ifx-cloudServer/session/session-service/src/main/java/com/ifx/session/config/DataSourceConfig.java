package com.ifx.session.config;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.function.Consumer;

@Configuration
public class DataSourceConfig {

    @Bean
    public SqlSessionTemplate sqlSessionTemplate( DataSource dataSource) throws Exception {
        SqlSessionTemplate sqlSessionTemplate
                =new SqlSessionTemplate(sqlSessionFactory(dataSource).getObject(), ExecutorType.BATCH);
        return sqlSessionTemplate;
    }
//    @Bean
//    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean (  DataSource dataSource){
//        MybatisSqlSessionFactoryBean mpSession = new MybatisSqlSessionFactoryBean();
//        mpSession.setDataSource(dataSource);
//        return mpSession;
//    }

    @Bean
    @ConditionalOnMissingBean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        // TODO 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        return factory;
    }
    /**
     * 检查spring容器里是否有对应的bean,有则进行消费
     *
     * @param clazz    class
     * @param consumer 消费
     * @param <T>      泛型
     */
    private <T> void getBeanThen(Class<T> clazz, Consumer<T> consumer) {
        if (SpringUtil.getBeanNamesForType(clazz).length > 0) {
            consumer.accept(SpringUtil.getBean(clazz));
        }
    }
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
//        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
//        scannerConfigurer.setBasePackage("com.ifx.*.mapper");
//        return scannerConfigurer;
//    }



}
