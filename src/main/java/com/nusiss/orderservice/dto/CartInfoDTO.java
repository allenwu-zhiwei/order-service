package com.nusiss.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author jyc
 * @Date 2024/10/12 下午10:13
 */
@Data
public class CartInfoDTO {

    //商品id
    private Long productId;

    //数量
    private Integer quantity;

    //是否选中
    private Integer isSelected;

    //价格(单价)
    private BigDecimal price;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;
}
