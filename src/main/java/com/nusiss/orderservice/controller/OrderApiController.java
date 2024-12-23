package com.nusiss.orderservice.controller;

import com.nusiss.orderservice.client.InventoryApiClient;
import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jyc
 * @Date 2024/10/13 上午3:02
 */
@RestController
@RequestMapping("/order/inner")
public class OrderApiController {
    @Resource
    private OrderService orderService;

    // 支付成功回调
    @GetMapping("/paySuccess")
    public ApiResponse paySuccess(Long orderId) {
        orderService.paySuccess(orderId);
        return ApiResponse.success();
    }

    @GetMapping("/getOrderInfo/{orderId}")
    Order getOrderById(@PathVariable("orderId") String orderId){
        return orderService.getById(orderId);
    }
}
