package com.yyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiFilesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiFilesApplication.class,args);
    }
}
