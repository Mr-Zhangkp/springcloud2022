package com.zhang.springcloud.service;

import com.zhang.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaomi
 * @date 2022/6/5
 */
public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
