package com.nusiss.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author jyc
 * @Date 2024/10/13 上午3:12
 */
@Data
public class OrderInfoDTO {
    private Long orderId;
    private Long userId;
    private Date orderDate;
    private BigDecimal totalPrice;
    private Object status;
    private String createUser;
    private String updateUser;
    private Date createDatetime;
    private Date updateDatetime;
    private List<CartInfoDTO> cartInfoList;
}
