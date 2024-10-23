package com.nusiss.orderservice.mq;

/**
 * @Author jyc
 * @Date 2024/10/23 下午8:26
 */

public class InventoryMessage {
    private Long productId;
    private int quantity;

    // 构造函数、getter和setter省略

    public InventoryMessage(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter和Setter方法
}