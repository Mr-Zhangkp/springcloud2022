package com.zhang.springcloud.service;

import com.zhang.springcloud.entities.CommonResult;
import com.zhang.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xiaomi
 * @date 2022/6/7
 */
@Component
@FeignClient(value = "cloud-payment-service")
public interface PaymentFeignService {

    /**
     * 演示OpenFeign超时控制
     * @return
     */
    @GetMapping("/payment/timeout")
    String paymentTimeout();

    @PostMapping("/payment/create")
    //@RequestBody: 把请求参数映射到实体类
    CommonResult<Payment> create(@RequestBody Payment payment);

    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id")Long id);
}
