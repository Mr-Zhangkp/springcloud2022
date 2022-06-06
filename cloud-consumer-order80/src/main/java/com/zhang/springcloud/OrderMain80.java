package com.zhang.springcloud;

import com.zhang.myrule.MySelfRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
@SpringBootApplication

//@EnableEurekaClient使该服务注册进Eureka Server
@EnableEurekaClient

//@RibbonClient使该微服务可以配置Ribbon负载均衡算法
//使用name指定调用哪个服务,使用configuration指定负载均衡算法的配置类
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)

public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);

    }
}
