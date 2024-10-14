package com.nusiss.orderservice.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order_Item
 */
@TableName(value ="order_Item")
@Data
public class OrderItem implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long orderItemId;

    /**
     * 
     */
    private Long orderId;

    /**
     * 
     */
    private Long productId;

    /**
     * 
     */
    private Integer quantity;

    /**
     * 
     */
    private BigDecimal price;

    /**
     * 
     */
    private String createUser;

    /**
     * 
     */
    private String updateUser;

    /**
     * 
     */
    private Date createDatetime;

    /**
     * 
     */
    private Date updateDatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}