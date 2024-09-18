package com.start.spring.springboot_tutorial.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
//    @Value("${cors.allowed.origins}")
//    private String[] allowedCorSOrigins;
@Value("cors.allowed.path")
@Autowired
String corsAllowedPath;

@Value("cors.allowed.origins")
@Autowired
String corsAllowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String pathName;

// NOT working
//                registry.addMapping("/departments").allowedOrigins("http://localhost:8091");
//                registry.addMapping("/departments/*").allowedOrigins("http://127.0.0.1:8091");
//                registry.addMapping("/departments*").allowedOrigins("http://127.0.0.1:8091, http://127.0.0.1:4200");
//                registry.addMapping("/departments*").allowedOrigins("http://127.0.0.1:8091; http://127.0.0.1:4200");

//Works
//                registry.addMapping("/departments/**")
//                        .allowedOrigins("http://127.0.0.1:4200, http://127.0.0.1:8091")
                registry.addMapping(corsAllowedPath)
                        .allowedOrigins(corsAllowedOrigins)
//                        .allowedOriginPatterns("*") //also works
                        .allowCredentials(false) //Should be false for "*" mapping
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .maxAge(60*30)
                        .exposedHeaders("*");
            }
        };
    }
}
