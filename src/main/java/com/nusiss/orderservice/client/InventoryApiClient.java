package com.nusiss.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("inventory-service")
public interface InventoryApiClient {
    //检查是否有库存
    @GetMapping("/inventory/inner/checkStock/{productId}/{num}")
    public Boolean checkStock(@PathVariable("productId") Long productId, @PathVariable("num") Integer num);

    //扣减库存
    @GetMapping("/inventory/inner/deductStock/{productId}/{num}")
    public Boolean deductStock(@PathVariable("productId") Long productId, @PathVariable("num") Integer num);
}
