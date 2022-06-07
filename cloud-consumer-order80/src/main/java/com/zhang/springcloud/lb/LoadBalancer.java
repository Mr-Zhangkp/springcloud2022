package com.zhang.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author xiaomi
 * @date 2022/6/6
 */
public interface LoadBalancer {

    ServiceInstance getInstance(List<ServiceInstance> serverInstances);
}
