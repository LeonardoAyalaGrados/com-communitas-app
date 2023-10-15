package com.communitas.store.app;

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
		public void addCorsMappings(CorsRegistry registry ) {
			registry.addMapping("/login")
			.allowedOriginPatterns("http://localhost:4200")
			.allowedMethods("POST")
			.exposedHeaders("*")
			.allowedHeaders("*");
		}
		};
	}
}
