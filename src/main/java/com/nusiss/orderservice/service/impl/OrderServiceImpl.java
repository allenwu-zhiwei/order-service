package com.nusiss.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.service.OrderService;
import com.nusiss.orderservice.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author jyc
* @description 针对表【order】的数据库操作Service实现
* @createDate 2024-10-11 00:45:49
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




