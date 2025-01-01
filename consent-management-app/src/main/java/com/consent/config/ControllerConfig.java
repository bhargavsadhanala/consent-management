package com.consent.config;

import com.consent.controller.UserController;
import com.consent.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public UserController userController(UserServiceImpl userServiceImpl) {
        return new UserController(userServiceImpl);
    }
}
