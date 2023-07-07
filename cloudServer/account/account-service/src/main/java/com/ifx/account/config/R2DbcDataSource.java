package com.ifx.account.config;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/23
 */
public class R2DbcDataSource {

//    @Bean
//    public void mysqlR2dbcConnectionFactory(@Autowired R2dbcProperties r2dbcProperties ){
//        Map<String, String> properties = r2dbcProperties.getProperties();
////        MySQLConnectionFactory mySQLConnectionFactory = new MySQLConnectionFactory(new Configuration());
//
//        PoolConfiguration poolConfiguration = new PoolConfiguration(
//                100,                            // maxObjects
//                TimeUnit.MINUTES.toMillis(15),  // maxIdle
//                10_000,                         // maxQueueSize
//                TimeUnit.SECONDS.toMillis(30)   // validationInterval
//        );
//        Connection connection = new ConnectionPool<>(
//                new MySQLConnectionFactory(new Configuration(
//                        "username",
//                        "host.com",
//                        3306,
//                        "password",
//                        "schema"
//                )), poolConfiguration);
//        connection.connect().get();
//        new DatabaseClient()
//        new
//        new R2dbcEntityTemplate()
//    }
}
