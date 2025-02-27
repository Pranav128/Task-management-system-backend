/*
package com.app.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", // Name of the security scheme
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // HTTP-based security
                                        .scheme("bearer") // Bearer token
                                        .bearerFormat("JWT"))) // JWT format
                .addSecurityItem(new SecurityRequirement().addList("bearer-key")); // Apply security globally
    }
}

 */