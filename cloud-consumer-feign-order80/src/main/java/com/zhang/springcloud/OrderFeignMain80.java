package com.zhang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaomi
 * @date 2022/6/7
 */
@SpringBootApplication

//@EnableFeignClients使此微服务可以使用@FeignClient注解
@EnableFeignClients

public class OrderFeignMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain80.class,args);
    }
}
