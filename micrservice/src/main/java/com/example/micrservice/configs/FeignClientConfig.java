package com.example.micrservice.configs;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
