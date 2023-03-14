package com.yyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  //开启consul注册
public class AdminUsersApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(AdminUsersApplicaiton.class,args);
    }
}
