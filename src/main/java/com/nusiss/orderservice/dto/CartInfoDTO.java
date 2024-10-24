package com.nusiss.orderservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author jyc
 * @Date 2024/10/24 下午5:36
 */

@Data
public class CartInfoDTO {

    private Long cartItemId;

    private Long productId;

    private String productName;

    private int quantity;

    private double price;

    private LocalDateTime createDatetime = LocalDateTime.now();

    private LocalDateTime updateDatetime = LocalDateTime.now();

    private String createUser;

    private String updateUser;
}
