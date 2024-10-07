package com.example.demo.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/api/**"}; // Adjust this to your actual API path
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch(paths)
                .build();
    }
}