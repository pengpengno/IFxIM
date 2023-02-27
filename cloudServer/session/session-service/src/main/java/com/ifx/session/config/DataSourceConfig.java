package com.ifx.session.config;

//import cn.hutool.extra.spring.SpringUtil;;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.Optional;
//import java.util.function.Consumer;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate( DataSource dataSource) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory(dataSource).getObject(), ExecutorType.BATCH);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
//        // TODO 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
//        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
//        factory.setDataSource(dataSource);
//        return factory;
//    }
//
//    /**
//     * 检查spring容器里是否有对应的bean,有则进行消费
//     *
//     * @param clazz    class
//     * @param consumer 消费
//     * @param <T>      泛型
//     */
//    private <T> void getBeanThen(Class<T> clazz, Consumer<T> consumer) {
//        if (SpringUtil.getBeanNamesForType(clazz).length > 0) {
//            consumer.accept(SpringUtil.getBean(clazz));
//        }
//    }



}
