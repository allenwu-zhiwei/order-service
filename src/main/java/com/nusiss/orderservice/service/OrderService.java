package com.nusiss.orderservice.service;

import com.nusiss.orderservice.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nusiss.orderservice.dto.CartInfoDTO;
import com.nusiss.orderservice.param.SubmitOrderParam;

import java.math.BigDecimal;
import java.util.List;

/**
* @author jyc
* @description 针对表【order】的数据库操作Service
* @createDate 2024-10-11 00:45:49
*/
public interface OrderService extends IService<Order> {

    int addOrder(Order order);

    List<Order> getOrderByUserId(long l);

    Long submitOrder(Long userId, BigDecimal totalPrice, List<CartInfoDTO> cartInfoList);

    void paySuccess(Long orderId);
}
