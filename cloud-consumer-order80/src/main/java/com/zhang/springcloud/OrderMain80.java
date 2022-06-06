package com.zhang.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
@SpringBootApplication

//@EnableEurekaClient使该服务注册进Eureka Server
@EnableEurekaClient

public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);

    }
}
