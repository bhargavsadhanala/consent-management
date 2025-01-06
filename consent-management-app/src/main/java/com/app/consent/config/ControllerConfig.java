package com.app.consent.config;

import com.app.consent.controller.UserController;
import com.app.consent.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public UserController userController(UserServiceImpl userServiceImpl) {
        return new UserController(userServiceImpl);
    }
}
