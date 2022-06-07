package com.zhang.springcloud.lb.impl;

import com.zhang.springcloud.lb.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaomi
 * @date 2022/6/6
 */
@Component
public class MyLB implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("***第几次访问: " + next);
        return next;
    }

    @Override
    public ServiceInstance getInstance(List<ServiceInstance> serverInstances) {
        int index = getAndIncrement() % serverInstances.size();
        return serverInstances.get(index);
    }
}
