package com.UserService.User.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/person/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/person/verifyAccount").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/person/alluser").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
