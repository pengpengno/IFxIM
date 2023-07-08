package com.ifx.account.R2DBC;

import com.ifx.account.AccountApplication;
import com.ifx.account.entity.ChatMsg;
import com.ifx.account.repository.ChatMsgRepository;
import io.r2dbc.spi.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/2
 */
@Slf4j
@SpringBootTest(classes = {AccountApplication.class})
public class MysqlConnect {


    @Autowired
    private ChatMsgRepository chatMsgRepository;

    public ConnectionFactory  createConnectFactory(){
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, "mysql")
                .option(HOST, "127.0.0.1")
                .option(USER, "if")
                .option(PORT, 3306)  // optional, default 3306
                .option(PASSWORD, "ifx2022") // optional, default null, null means has no password
                .option(DATABASE, "ifx") // optional, default null, null means not specifying the database
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

    @Test
    public void  testConnectMysql(){


//        new MySqlConnectionFactoryProvider()
        ConnectionFactory connectFactory = createConnectFactory();
        Mono<? extends Publisher<? extends Result>> map = Mono.from(connectFactory.create())
                .map(e -> e.createStatement("select * from account").fetchSize(10))
                .doOnNext(e -> log.info(e.toString()))
                .map(e -> e.execute());


        StepVerifier.create(map
                )
                .expectError();

    }

    @Test
    public void testMsgSelect (){
        ConnectionFactory connectionFactory = createConnectFactory();

        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
//
//        template.getDatabaseClient().sql("CREATE TABLE person" +
//                        "(id VARCHAR(255) PRIMARY KEY," +
//                        "name VARCHAR(255)," +
//                        "age INT)")
//                .fetch()
//                .rowsUpdated()
//                .as(StepVerifier::create)
//                .expectNextCount(1)
//                .verifyComplete();

//        template.insert(Person.class)
//                .using(new Person("joe", "Joe", 34))
//                .as(StepVerifier::create)
//                .expectNextCount(1)
//                .verifyComplete();
        Long sessionId = 1644223948273614848l;
        String startTime = "2023-06-12";
        String endTime = "2023-07-12";

        template.select(ChatMsg.class)
                        .first()
                .doOnNext(e->log.info("{}",e.toString()))
                .doOnNext(System.out::println)
                .log()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .expectComplete();

       chatMsgRepository.findByIdAndCreateTimeBetween(sessionId, startTime, endTime)
               .doOnNext(e->log.info("{}",e.toString()))
               .doOnNext(System.out::println)
               .log()
               .as(StepVerifier::create)
               .expectNextCount(1)
               .expectComplete();

    }
}
