package com.zhang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaomi
 * @date 2022/6/7
 */
@SpringBootApplication

//@EnableFeignClients使此微服务可以使用@FeignClient注解
@EnableFeignClients

//EnableHystrix 启用Hystrix
@EnableHystrix

public class OrderHystrixMain80 {
    public static void main(String[] args) {

        SpringApplication.run(OrderHystrixMain80.class,args);
    }
}
