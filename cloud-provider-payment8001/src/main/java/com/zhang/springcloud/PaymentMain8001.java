package com.zhang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
@SpringBootApplication

//@EnableEurekaClient使该微服务注册进Eureka Server
@EnableEurekaClient

//@EnableDiscoveryClient使该微服务可以发现eureka server里面注册了哪些服务,并获取这些服务的信息
@EnableDiscoveryClient

public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
