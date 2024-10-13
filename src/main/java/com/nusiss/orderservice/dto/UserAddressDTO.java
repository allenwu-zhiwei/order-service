package com.nusiss.orderservice.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author jyc
 * @Date 2024/10/12 下午6:34
 */
@Data
public class UserAddressDTO {

    private Long addressId;

    private Long userId;

    private String street;

    private String city;

    private String state;

    private String createUser;

    private String updateUser;

    private Date createDatetime;

    private Date updateDatetime;
}
