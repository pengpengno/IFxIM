package com.ifx.extraResource.autoconfigure;

import com.ifx.extraResource.properties.DataBaseProperties;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

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

    @Bean
    @ConditionalOnMissingBean(DatabaseClient.class)
    @ConditionalOnBean(ConnectionFactory.class)
    public DatabaseClient buildDataBaseClient(ConnectionFactory connectionFactory){
        return DatabaseClient.create(connectionFactory);
    }
}
