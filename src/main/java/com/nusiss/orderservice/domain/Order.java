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
 * @TableName order
 */
@TableName(value ="order")
@Data
public class Order implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Date orderDate;

    /**
     * 
     */
    private BigDecimal totalPrice;

    /**
     * 
     */
    private Object status;

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