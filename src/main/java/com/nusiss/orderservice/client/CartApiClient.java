package com.nusiss.orderservice.client;

import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.dto.CartInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient("cart-service")
public interface CartApiClient {
    // 查询购物车中所有被选中的商品
    @GetMapping("/cart/inner/getCartCheckedList")
    public List<CartInfoDTO> getCartCheckedList(Long userId);

    // 删除购物车中已经下单的商品(即为选中的商品)
    @PutMapping("/cart/inner/deleteProductsInOrder/{userId}")
    public ApiResponse deleteCartProductsInOrder(@PathVariable("userId") Long userId);
}
