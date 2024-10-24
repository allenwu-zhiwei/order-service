package com.nusiss.orderservice.service;

import com.nusiss.orderservice.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nusiss.orderservice.param.SubmitOrderParam;

import java.util.List;

/**
* @author jyc
* @description 针对表【order】的数据库操作Service
* @createDate 2024-10-11 00:45:49
*/
public interface OrderService extends IService<Order> {

    int addOrder(Order order);

    List<Order> getOrderByUserId(long l);

    Long submitOrder(SubmitOrderParam submitOrderParam);

    void paySuccess(Long orderId);
}
