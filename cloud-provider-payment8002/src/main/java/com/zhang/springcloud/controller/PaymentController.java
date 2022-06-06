package com.zhang.springcloud.controller;

import com.zhang.springcloud.entities.CommonResult;
import com.zhang.springcloud.entities.Payment;
import com.zhang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
@RestController
@Slf4j//用来打印日志
public class PaymentController {

    @Resource
    private PaymentService paymentService;


    /**
     * 从配置文件中获取端口号,放到result中,这样我们就能看到调用这个微服务的时候调用的哪个端口
     * 因为同一个微服务名字下面可能是个集群,功能一样,端口号不一样,显示端口号能体现出负载均衡的效果
     */
    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
                                        //@RequestBody: 把请求参数映射到实体类
    public CommonResult<Payment> create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果: " + result);

        if(result > 0){
            return new CommonResult (200,"插入成功, " + serverPort, result);
        }else {
            return new CommonResult<>(19999,"插入失败", null);
        }
    }
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        Payment payment= paymentService.getPaymentById(id);
        log.info("查询结果: " + payment);

        if(payment != null){
            return new CommonResult<>(200, "查询成功, " + serverPort, payment);
        }else {
            return new CommonResult<>(19998, "查询失败,没有id: " + id, null);
        }
    }
}
