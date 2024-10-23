package com.nusiss.orderservice.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author jyc
 * @Date 2024/10/23 下午8:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMessage{
    private Long productId;
    private int quantity;
    private Long orderId;
}