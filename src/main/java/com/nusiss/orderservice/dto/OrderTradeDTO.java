package com.nusiss.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author jyc
 * @Date 2024/10/13 上午12:34
 * 确认订单界面给前端返回的对象，展示订单信息
 */
@Data
public class OrderTradeDTO {
    private Date OrderDate;
    private BigDecimal totalPrice;
    private List<CartInfoDTO> cartInfoList;
}
