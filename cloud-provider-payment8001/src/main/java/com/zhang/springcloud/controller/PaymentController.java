package com.zhang.springcloud.controller;

import com.zhang.springcloud.entities.CommonResult;
import com.zhang.springcloud.entities.Payment;
import com.zhang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * DiscoveryClient用于获取eureka server上面注册了哪些服务,以及这些服务的基本信息
     */
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 通过DiscoveryClient获取eureka server上注册的服务的信息
     * @return
     */
    @GetMapping("/payment/discovery")
    public Object discovery(){
        //查看注册了哪些服务
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*****service: " + service);
            //查看一个服务下有几个实例(节点)
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                log.info("**instance: " + instance);
                log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" +
                        instance.getPort() + "\t" + instance.getUri());
            }
        }
        return this.discoveryClient;
    }

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
