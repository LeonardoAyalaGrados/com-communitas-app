package com.communitas.store.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	@Bean
	public WebMvcConfigurer corsOrigin(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/login")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("POST")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .exposedHeaders("*");
            }
        };
    }
}
