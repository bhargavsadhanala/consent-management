package com.consent.config;

import com.consent.repository.UserRepository;
import com.consent.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public UserServiceImpl userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

}
