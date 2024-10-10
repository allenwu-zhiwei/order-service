package com.nusiss.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nusiss.orderservice.domain.OrderItem;
import com.nusiss.orderservice.service.OrderItemService;
import com.nusiss.orderservice.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author jyc
* @description 针对表【order_Item】的数据库操作Service实现
* @createDate 2024-10-11 00:54:50
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService {

}




