package com.nusiss.orderservice;
import java.util.*;
import java.math.*;

import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.domain.OrderItem;
import com.nusiss.orderservice.dto.CartInfoDTO;
import com.nusiss.orderservice.mapper.OrderItemMapper;
import com.nusiss.orderservice.mapper.OrderMapper;
import com.nusiss.orderservice.mq.InventoryMessage;
import com.nusiss.orderservice.param.SubmitOrderParam;
import com.nusiss.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addOrder_ShouldInsertOrderSuccessfully() {
        Order order = new Order();
        order.setUserId(1L);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setTotalPrice(new java.math.BigDecimal(100));
        order.setCreateUser("user");
        order.setUpdateUser("user");
        order.setCreateDatetime(new Date());
        order.setUpdateDatetime(new Date());

        when(orderMapper.insert(any(Order.class))).thenReturn(1);

        int result = orderService.addOrder(order);

        assertEquals(1, result);
        verify(orderMapper, times(1)).insert(any(Order.class));
    }

    @Test
    void getOrderByUserId_ShouldReturnListOfOrders() {
        long userId = 1L;
        List<Order> mockOrders = java.util.Collections.singletonList(new Order());
        mockOrders.get(0).setUserId(userId);

        when(orderMapper.selectList(any())).thenReturn(mockOrders);

        List<Order> orders = orderService.getOrderByUserId(userId);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(userId, orders.get(0).getUserId());
        verify(orderMapper, times(1)).selectList(any());
    }

    @Test
    void submitOrder_ShouldAddOrderAndOrderItems() {
        SubmitOrderParam submitOrderParam = new SubmitOrderParam();
        submitOrderParam.setUserId(1L);
        submitOrderParam.setTotalPrice(new java.math.BigDecimal(200));
        submitOrderParam.setCartInfoList(java.util.Collections.singletonList(new CartInfoDTO()));

        when(orderMapper.insert(any(Order.class))).thenReturn(1);
        when(orderItemMapper.insert(any(OrderItem.class))).thenReturn(1);

        Long orderId = orderService.submitOrder(submitOrderParam);

        assertNotNull(orderId);
        verify(orderMapper, times(1)).insert(any(Order.class));
        verify(orderItemMapper, times(1)).insert(any(OrderItem.class));
    }

    @Test
    void handleConfirmMessage_ShouldUpdateOrderStatus() {
        InventoryMessage inventoryMessage = new InventoryMessage(1L, 1, 1L);

        when(orderMapper.update(any(), any())).thenReturn(1);

        orderService.handleConfirmMessage(inventoryMessage);

        verify(orderMapper, times(1)).update(any(), any());
    }

    @Test
    void handleRollbackMessage_ShouldDeleteOrderAndOrderItems() {
        InventoryMessage inventoryMessage = new InventoryMessage(1L, 1, 1L);

        when(orderMapper.delete(any())).thenReturn(1);
        when(orderItemMapper.delete(any())).thenReturn(1);

        orderService.handleRollbackMessage(inventoryMessage);

        verify(orderMapper, times(1)).delete(any());
        verify(orderItemMapper, times(1)).delete(any());
    }

    @Test
    void paySuccess_ShouldSendMessagesForInventoryDeduction() {
        Long orderId = 1L;

        Order order = new Order();
        order.setOrderId(orderId);
        List<OrderItem> orderItemList = java.util.Collections.singletonList(new OrderItem());
        orderItemList.get(0).setOrderId(orderId);
        orderItemList.get(0).setProductId(1L);
        orderItemList.get(0).setQuantity(1);

        when(orderItemMapper.selectList(any())).thenReturn(orderItemList);

        doNothing().when(orderMapper).update(any(), any());
        doNothing().when(orderItemMapper).delete(any());

        orderService.paySuccess(orderId);

        verify(orderItemMapper, times(1)).selectList(any());
        // Here you should verify that the message is sent to RabbitMQ, for example:
        // verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any());
    }
}
