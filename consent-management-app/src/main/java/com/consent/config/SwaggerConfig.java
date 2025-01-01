package com.consent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String CONTROLLER_PACKAGE = "com.consent.controller";

    @Bean
    public OpenAPI api(
            @Value("${project.version:unknown}") String version
    ) {
        return new OpenAPI()
                .info(new Info().title("Consent Management APIs")
                        .description("User Consent Management")
                        .version(version)
                );
    }

    @Bean
    public GroupedOpenApi userAPIs() {
        String[] paths = { "/api/v1/users/**" };
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch(paths)
                .packagesToScan(CONTROLLER_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi purposeAPIs() {
        String[] paths = { "/api/v1/purposes/**" };
        return GroupedOpenApi.builder()
                .group("Purpose")
                .pathsToMatch(paths)
                .packagesToScan(CONTROLLER_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi consentAPIs() {
        String[] paths = { "/api/v1/consent/**" };
        return GroupedOpenApi.builder()
                .group("Consent")
                .pathsToMatch(paths)
                .packagesToScan(CONTROLLER_PACKAGE)
                .build();
    }
}
