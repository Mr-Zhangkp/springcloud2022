package com.zhang.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaomi
 * @date 2022/6/7
 */
@Configuration
public class FeignConfig {
    /**
     * Feign提供了日志打印功能,对Feign接口的调用情况进行监控,
     * 可以让我们了解Feign中http请求的细节
     * 需要配置bean和yml
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel(){

        /*
            NONE 默认,不显示任何日志
            BASIC 仅记录请求方法,URL,响应状态码,执行时间
            HEADERS 除了BASIC中的内容,还有请求和响应头信息
            FULL 除了HEADERS中的内容,还有请求响应的正文及元数据
         */
        return Logger.Level.FULL;
    }
}
