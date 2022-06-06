package com.zhang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
@SpringBootApplication

@EnableEurekaServer
//@EnableEurekaServer代表此微服务是eureka server,即注册中心

public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class, args);
    }
}
