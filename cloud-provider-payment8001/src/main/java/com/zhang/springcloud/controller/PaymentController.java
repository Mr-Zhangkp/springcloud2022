package com.zhang.springcloud.controller;

import com.zhang.springcloud.entities.CommonResult;
import com.zhang.springcloud.entities.Payment;
import com.zhang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.DefaultSelectorProvider;

import javax.annotation.Resource;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
@RestController
@Slf4j//用来打印日志
@SuppressWarnings({"all"})
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
     * 测试OpenFegn超时控制
     * @return
     */
    @GetMapping("/payment/timeout")
    public String paymentTimeout(){
        //模拟一个3s的业务
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }


    /**
     * 测试手写轮询算法,需要注释掉调用端80RestTemplate上面的@loadbalanced
     * @return
     */
    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }


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
            //轮询算法: rest请求次数 % 集群数量 = List的下标
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


        //AtomicInteger
 /*       static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    public static void main1(String[] args) {
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();
        new Thread(()->{
            //暂停1s,确保t1先执行一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 666) + "\t" + atomicReference.get());
        },"t2").start();
    }

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        new Thread(()->{
            //获取版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号: " + stamp);
            //暂停1s.确保执行ABA之前t2先获取版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,
                    atomicStampedReference.getStamp(),atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第二次版本号: " +atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,
                    atomicStampedReference.getStamp(),atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第三次版本号: " + atomicStampedReference.getStamp());
        },"t1").start();

        new Thread(()->{
            //获取版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号: " + stamp);
            //暂停3s.确保t1先执行一次ABA
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 666,
                    stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t修改结果: " +
                    result + "\t第二次版本号: " + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName() + "\t最新值: " + atomicStampedReference.getReference());
        },"t2").start();
    }*/



}
