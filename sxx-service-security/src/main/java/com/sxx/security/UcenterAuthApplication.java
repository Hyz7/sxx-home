package com.sxx.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@EntityScan("com.sxx.framework.domain.ucenter")//扫描实体类
@ComponentScan(basePackages={"com.sxx.api"})//扫描接口
@ComponentScan(basePackages={"com.sxx.framework"})//扫描common下的所有类
@SpringBootApplication
public class UcenterAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterAuthApplication.class, args);
    }


}
