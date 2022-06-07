package com.zhang.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    /**
     * 使用了服务名称的方式访问服务,而非写死的地址
     * 此时RestTemplate不知道要访问集群下面的具体哪个节点
     * 需要加一个@LocdBalanced注解实现负载均衡
     */

    //@LoadBalanced
    //测试自己写的轮询算法,需要注释掉LoadBalanced注解

    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
