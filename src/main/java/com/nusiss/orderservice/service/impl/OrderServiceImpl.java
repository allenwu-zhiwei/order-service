package com.nusiss.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nusiss.orderservice.client.InventoryApiClient;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.domain.OrderItem;
import com.nusiss.orderservice.dto.CartInfoDTO;
import com.nusiss.orderservice.mapper.OrderItemMapper;
import com.nusiss.orderservice.param.SubmitOrderParam;
import com.nusiss.orderservice.service.OrderService;
import com.nusiss.orderservice.mapper.OrderMapper;
import feign.QueryMap;
import jakarta.annotation.Resource;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author jyc
* @description 针对表【order】的数据库操作Service实现
* @createDate 2024-10-11 00:45:49
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private InventoryApiClient inventoryApiClient;

    @Override
    public int addOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public List<Order> getOrderByUserId(long userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("order_date");
        // 限制查询10条数据
        queryWrapper.last("limit 10");
        return orderMapper.selectList(queryWrapper);
    }

    @Override
    public Long submitOrder(SubmitOrderParam submitOrderParam) {
        Order order = new Order();
        order.setUserId(submitOrderParam.getUserId());
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setTotalPrice(submitOrderParam.getTotalPrice());
        order.setCreateUser("jyc");
        order.setUpdateUser("jyc");
        order.setCreateDatetime(new Date());
        order.setUpdateDatetime(new Date());
        orderMapper.insert(order);
        System.out.println(order);
        // 对订单里每个商品插入order_item
        List<CartInfoDTO> cartInfoList = submitOrderParam.getCartInfoList();
        for (CartInfoDTO cartInfoDTO : cartInfoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(cartInfoDTO.getProductId());
            orderItem.setQuantity(cartInfoDTO.getQuantity());
            orderItem.setPrice(cartInfoDTO.getPrice());
            orderItem.setCreateUser("jyc");
            orderItem.setUpdateUser("jyc");
            orderItem.setCreateDatetime(new Date());
            orderItem.setUpdateDatetime(new Date());
            orderItemMapper.insert(orderItem);
        }
        return order.getOrderId();
    }

    @Override
    public void paySuccess(Long orderId) {
        // 修改ID为orderId的订单状态为已支付
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id",orderId);
        updateWrapper.set("status","PAID");
        orderMapper.update(null,updateWrapper);
        // 扣减库存
        List<OrderItem> orderItemList = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderId));
        for (OrderItem orderItem : orderItemList) {
            inventoryApiClient.deductStock(orderItem.getProductId(),orderItem.getQuantity());
        }
    }
}




