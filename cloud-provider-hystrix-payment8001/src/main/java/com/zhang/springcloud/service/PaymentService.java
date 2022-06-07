package com.zhang.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaomi
 * @date 2022/6/7
 */
@Service

public class PaymentService {

    /**
     * 正常访问的方法 肯定ok
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池:  " + Thread.currentThread().getName() +
                "  paymentInfo_OK, id: " + id + "\t哈哈哈";
    }
    /**
     * 超时的方法
     * @param id
     * @return
     *
     * @HystrixCommand 标注的方法如果超时会有兜底方法处理
     * 兜底方法为 fallbackMethod属性 指定的 paymentInfo_TimeoutHandler
     *
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "3000") //超过3秒不能返回结果则走兜底方法
    })
    public String paymentInfo_Timeout(Integer id){
        int timeout = 5;
        //演示超时的降级

        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //演示异常的降级

        //int age = 10/0;

        return "线程池:  " + Thread.currentThread().getName() +
                "  paymentInfo_Timeout, id: " + id + "\t哈哈哈, 耗时" + timeout + "s";
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "线程池:  " + Thread.currentThread().getName() +
                "  paymentInfo_Timeout, id: " + id + "\t您要的网页飞走了/(ㄒoㄒ)/~~";
    }

}
