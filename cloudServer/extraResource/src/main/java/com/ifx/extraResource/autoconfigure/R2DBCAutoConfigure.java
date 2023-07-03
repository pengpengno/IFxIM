package com.ifx.extraResource.autoconfigure;

import com.ifx.extraResource.properties.DataBaseProperties;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsBuilderCustomizer;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsInitializer;
import org.springframework.boot.autoconfigure.r2dbc.MissingR2dbcPoolDependencyException;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.r2dbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.util.ClassUtils;

import java.util.List;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/3
 */


@EnableConfigurationProperties(DataBaseProperties.class)
public class R2DBCAutoConfigure {





    @Bean
    @ConditionalOnMissingBean(ConnectionFactory.class)
    @ConditionalOnBean(DataBaseProperties.class)
    public ConnectionFactory buildConnection(DataBaseProperties dataBaseProperties){
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, dataBaseProperties.getDriver())
                .option(HOST, dataBaseProperties.getHost())
                .option(USER, dataBaseProperties.getUser())
                .option(PORT, dataBaseProperties.getPort())  // optional, default 3306
                .option(PASSWORD, dataBaseProperties.getPassword()) // optional, default null, null means has no password
                .option(DATABASE, dataBaseProperties.getDataBase()) // optional, default null, null means not specifying the database
//                .option(CONNECT_TIMEOUT, Duration.ofSeconds(3)) // optional, default null, null means no timeout
//                .option(Option.valueOf("socketTimeout"), Duration.ofSeconds(4)) // deprecated since 1.0.1, because it has no effect and serves no purpose.
                .option(SSL, true) // optional, default sslMode is "preferred", it will be ignore if sslMode is set
//                .option(Option.valueOf("sslMode"), "verify_identity") // optional, default "preferred"
//                .option(Option.valueOf("sslCa"), "/path/to/mysql/ca.pem") // required when sslMode is verify_ca or verify_identity, default null, null means has no server CA cert
//                .option(Option.valueOf("sslCert"), "/path/to/mysql/client-cert.pem") // optional, default null, null means has no client cert
//                .option(Option.valueOf("sslKey"), "/path/to/mysql/client-key.pem") // optional, default null, null means has no client key
//                .option(Option.valueOf("sslKeyPassword"), "key-pem-password-in-here") // optional, default null, null means has no password for client key (i.e. "sslKey")
//                .option(Option.valueOf("tlsVersion"), "TLSv1.3,TLSv1.2,TLSv1.1") // optional, default is auto-selected by the server
//                .option(Option.valueOf("sslHostnameVerifier"), "com.example.demo.MyVerifier") // optional, default is null, null means use standard verifier
//                .option(Option.valueOf("sslContextBuilderCustomizer"), "com.example.demo.MyCustomizer") // optional, default is no-op customizer
//                .option(Option.valueOf("zeroDate"), "use_null") // optional, default "use_null"
//                .option(Option.valueOf("useServerPrepareStatement"), true) // optional, default false
                .option(Option.valueOf("tcpKeepAlive"), true) // optional, default false
//                .option(Option.valueOf("tcpNoDelay"), true) // optional, default false
//                .option(Option.valueOf("autodetectExtensions"), false) // optional, default false
                .build();
        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
        return connectionFactory;

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(ConnectionPool.class)
    static class PooledConnectionFactoryConfiguration {

        @Bean(destroyMethod = "dispose")
        ConnectionPool connectionFactory(R2dbcProperties properties, ResourceLoader resourceLoader,
                                         ObjectProvider<ConnectionFactoryOptionsBuilderCustomizer> customizers) {
            ConnectionFactory connectionFactory = createConnectionFactory(properties,
                    resourceLoader.getClassLoader(), customizers.orderedStream().toList());
            R2dbcProperties.Pool pool = properties.getPool();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            ConnectionPoolConfiguration.Builder builder = ConnectionPoolConfiguration.builder(connectionFactory);
            map.from(pool.getMaxIdleTime()).to(builder::maxIdleTime);
            map.from(pool.getMaxLifeTime()).to(builder::maxLifeTime);
            map.from(pool.getMaxAcquireTime()).to(builder::maxAcquireTime);
            map.from(pool.getMaxCreateConnectionTime()).to(builder::maxCreateConnectionTime);
            map.from(pool.getInitialSize()).to(builder::initialSize);
            map.from(pool.getMaxSize()).to(builder::maxSize);
            map.from(pool.getValidationQuery()).whenHasText().to(builder::validationQuery);
            map.from(pool.getValidationDepth()).to(builder::validationDepth);
            return new ConnectionPool(builder.build());
        }

    }
    protected static ConnectionFactory createConnectionFactory(R2dbcProperties properties, ClassLoader classLoader,
                                                               List<ConnectionFactoryOptionsBuilderCustomizer> optionsCustomizers) {
        try {
            return org.springframework.boot.r2dbc.ConnectionFactoryBuilder
                    .withOptions(new ConnectionFactoryOptionsInitializer().initialize(properties,
                            () -> EmbeddedDatabaseConnection.get(classLoader)))
                    .configure((options) -> {
                        for (ConnectionFactoryOptionsBuilderCustomizer optionsCustomizer : optionsCustomizers) {
                            optionsCustomizer.customize(options);
                        }
                    }).build();
        }
        catch (IllegalStateException ex) {
            String message = ex.getMessage();
            if (message != null && message.contains("driver=pool")
                    && !ClassUtils.isPresent("io.r2dbc.pool.ConnectionPool", classLoader)) {
                throw new MissingR2dbcPoolDependencyException();
            }
            throw ex;
        }
    }
    @Bean
    @ConditionalOnMissingBean(DatabaseClient.class)
    @ConditionalOnBean(ConnectionFactory.class)
    public DatabaseClient buildDataBaseClient(ConnectionFactory connectionFactory){
        return DatabaseClient.create(connectionFactory);
    }
}
