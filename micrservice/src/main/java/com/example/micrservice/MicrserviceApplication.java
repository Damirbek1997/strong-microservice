package com.example.micrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MicrserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrserviceApplication.class, args);
    }

}
