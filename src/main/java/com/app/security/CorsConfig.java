package com.app.security;

//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Value("${ALLOWED_ORIGINS}") // Inject from environment variable
//    private String allowedOrigins;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
//            String[] origins = allowedOrigins.split(","); // Handle multiple origins
//            registry.addMapping("/api/**")
//                    .allowedOrigins(origins)
//                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                    .allowedHeaders("*");
//        } else {
//            // Handle the case where ALLOWED_ORIGINS is not set (e.g., development)
//            registry.addMapping("/api/**")
//                    .allowedOrigins("http://localhost:4200")  // Default for local dev
//                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                    .allowedHeaders("*");
//        }
//    }
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins("http://localhost:4200") // Allow frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true) // Allow cookies/auth tokens
                        .maxAge(3600); // Cache preflight response for 1 hour
            }
        };
    }
}