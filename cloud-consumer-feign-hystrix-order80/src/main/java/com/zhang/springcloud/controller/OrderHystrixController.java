package com.zhang.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zhang.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiaomi
 * @date 2022/6/7
 */
@RestController
@Slf4j

//配置Hystrix的全局异常处理
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")

public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    /**
     * @HystrixCommand 标注的方法如果超时会有兜底方法处理
     * 兜底方法为 fallbackMethod属性 指定的 paymentInfo_TimeoutHandler
     * 注意: 消费端在controller中使用
     */
    /*@HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "5000") //超过1.5秒不能返回结果则走兜底方法
            // 此处时间小于提供者的兜底时间,会在1.5s后走此兜底方法
            //注意: 修改HystrixCommand中的属性之后需要重启服务才会生效
    })*/

    @HystrixCommand
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
        //测试异常导致的降级
        //int age = 10/0;

        //测试超时导致的降级
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }
    public String paymentInfo_TimeoutHandler(@PathVariable("id") Integer id){
        return "我是消费者80,提供者系统繁忙,或者自己出错/(ㄒoㄒ)/~~";
    }


    //全局fallback

    public String payment_Global_FallbackMethod(){
        return "Global异常处理方法,稍后再试/(ㄒoㄒ)/~~";
    }
}
