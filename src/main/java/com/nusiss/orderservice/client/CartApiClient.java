package com.nusiss.orderservice.client;

import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.dto.CartInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("cart-service")
public interface CartApiClient {
    // 查询购物车中所有被选中的商品
    @GetMapping("/api/v1/cart/selected-items")
    public List<CartInfoDTO> getCartCheckedList(@RequestHeader("authToken") String authToken);

    // 删除购物车中已经下单的商品(即为选中的商品)
    @PutMapping("/api/v1/cart/remove-selected-items")
    public ResponseEntity<String> deleteCartProductsInOrder(@RequestHeader("authToken") String authToken);
}
