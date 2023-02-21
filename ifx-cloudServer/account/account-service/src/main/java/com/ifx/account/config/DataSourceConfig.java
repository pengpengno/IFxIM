package com.ifx.account.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {


//
//    @Bean
//    public ConnectionFactory initConnectionFactory(@Autowired R2dbcProperties r2dbcProperties){
//            ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(r2dbcProperties.getUrl());
//            ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder().from(baseOptions);
////        MySqlConnectionFactoryProvider mySqlConnectionFactoryProvider = new MySqlConnectionFactoryProvider();
//        Connection connection = MySQLConnectionBuilder.createConnectionPool(
//                "jdbc:mysql://$host:$port/$database?user=$username&password=$password");
//
//        String username = r2dbcProperties.getUsername();
//            String password = r2dbcProperties.getPassword();
////        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
////                .option(ConnectionFactoryOptions.DRIVER, "a-driver")
////                .option(ConnectionFactoryOptions.PROTOCOL, "pipes")
////                .option(ConnectionFactoryOptions.HOST, "localhost")
////                .option(ConnectionFactoryOptions.PORT, 3306)
////                .option(ConnectionFactoryOptions.DATABASE, "my_database")
//////                .option(Option.valueOf("locale"), "en_US")
////                .build();
//
//        ConnectionFactoryOptions build =
//                ob.option(ConnectionFactoryOptions.USER, username)
//                .option(ConnectionFactoryOptions.PASSWORD, password)
//                .build();
//            return ConnectionFactories.find(build);
//    }
//        @Bean
//        SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {
//            DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
//                    new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
//            );
//            http
//                    .authorizeExchange((exchange) -> exchange.anyExchange().authenticated())
//                    .logout((logout) -> logout.logoutHandler(logoutHandler));
//
//            return http.build();
//        }

//    @Bean
//    public R2dbcEntityTemplate r2dbcEntityTemplate(@Autowired ConnectionFactory databaseClient){
//        return new R2dbcEntityTemplate(databaseClient);
//    }

}
