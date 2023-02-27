package com.ifx.account.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebFluxSecurity
public class OAuth2LoginSecurityConfig {

//   TODO 后续实现

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http .authorizeExchange(exchanges -> exchanges .anyExchange().authenticated() )
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults());
//        return http.build();
//    }
//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//        return new MapReactiveUserDetailsService(user);
//    }
}