package com.zhang.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.function.Predicate;

/**
 * @author xiaomi
 * @date 2022/6/6
 */
@RestController
@Slf4j
public class OrderConsulController {


    @Resource
    private RestTemplate restTemplate;

    public static final String INVOKE_URL = "http://consul-provider-payment";

    @RequestMapping("/consumer/payment/consul")
    public String paymentConsul(){
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
        return result;
    }
}
