package com.UserService.User.Validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PassWordConfig {
    @Bean
    public PasswordEncoder PassWordConfig() {
        return new BCryptPasswordEncoder();
    }
}
