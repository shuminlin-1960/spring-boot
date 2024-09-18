package com.start.spring.springboot_tutorial.config;

import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTraceConfig {
        @Bean
        public InMemoryHttpExchangeRepository createTraceRepository() {
            return new InMemoryHttpExchangeRepository();
        }

//    @Bean  NOt working for Spring 3.2.6
//    public HttpTraceRepository httpTraceRepository() {
//        return new InMemoryHttpTraceRepository();
//    }
}

