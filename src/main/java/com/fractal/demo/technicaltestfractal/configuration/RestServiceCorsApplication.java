package com.fractal.demo.technicaltestfractal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestServiceCorsApplication {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedHeaders("*")
					.allowedMethods("POST", "PUT", "DELETE", "GET", "PATCH")
					.allowedOrigins("http://localhost:3000", "https://master.dtavh8reg6sgl.amplifyapp.com")
					.allowCredentials(false).maxAge(3600);
			}
		};
	}
}
