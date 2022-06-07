package com.zhang.springcloud.controller;

import com.zhang.springcloud.entities.CommonResult;
import com.zhang.springcloud.entities.Payment;
import com.zhang.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xiaomi
 * @date 2022/6/7
 */
@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    /**
     * 演示OpenFeign超时控制
     * @return
     */
    @GetMapping("/consumer/payment/timeout")
    public String paymentTimeout(){
        return paymentFeignService.paymentTimeout();
    }

    @PostMapping("/consumer/payment/create")
        //@RequestBody: 把请求参数映射到实体类
    public CommonResult<Payment> create(@RequestBody Payment payment){
        return paymentFeignService.create(payment);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        return paymentFeignService.getPaymentById(id);
    }
}
