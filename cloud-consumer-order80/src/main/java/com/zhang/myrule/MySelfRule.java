package com.zhang.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaomi
 * @date 2022/6/6
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule(){

        //定义为随机
        return new RandomRule();
    }

}
