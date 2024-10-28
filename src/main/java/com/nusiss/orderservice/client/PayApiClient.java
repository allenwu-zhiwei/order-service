package com.nusiss.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@FeignClient("pay-service")
public interface PayApiClient {
    // 发起支付(待定，是否要提交订单的同时发起支付，如果删掉选择付款方式的界面就用这个发出请求)
    @GetMapping("/api/payment/pay")
    public String submitPayment(String orderId);
}
