package com.nusiss.orderservice.service;

import java.util.*;
import java.math.*;

import com.nusiss.orderservice.client.InventoryApiClient;
import com.nusiss.orderservice.config.RabbitConfig;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.domain.OrderItem;
import com.nusiss.orderservice.dto.CartInfoDTO;
import com.nusiss.orderservice.mapper.OrderItemMapper;
import com.nusiss.orderservice.mapper.OrderMapper;
import com.nusiss.orderservice.mq.InventoryMessage;
import com.nusiss.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private InventoryApiClient inventoryApiClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        // Initialize orderService if necessary (e.g., if constructor injection is used)
    }

    @Test
    void addOrder_InsertsOrder_ReturnsOne() {
        Order order = new Order();
        when(orderMapper.insert(any(Order.class))).thenReturn(1);

        int result = orderService.addOrder(order);

        assertEquals(1, result);
        verify(orderMapper, times(1)).insert(any(Order.class));
    }

    @Test
    void getOrderByUserId_ReturnsListOfOrders() {
        long userId = 1L;
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order());

        when(orderMapper.selectList(any())).thenReturn(expectedOrders);

        List<Order> orders = orderService.getOrderByUserId(userId);

        assertEquals(expectedOrders, orders);
        verify(orderMapper, times(1)).selectList(any());
    }

    @Test
    void submitOrder_InsertsOrderAndOrderItems_ReturnsOrderId() {
        Long userId = 1L;
        BigDecimal totalPrice = BigDecimal.valueOf(100);
        List<CartInfoDTO> cartInfoList = new ArrayList<>();
        cartInfoList.add(new CartInfoDTO());

        Long expectedOrderId = 1L;

        when(orderMapper.insert(any(Order.class))).thenReturn(1);
        when(orderItemMapper.insert(any(OrderItem.class))).thenReturn(1);

        Long orderId = orderService.submitOrder(userId, totalPrice, cartInfoList);

        assertEquals(expectedOrderId, orderId);
        verify(orderMapper, times(1)).insert(any(Order.class));
        verify(orderItemMapper, times(1)).insert(any(OrderItem.class));
    }

    @Test
    void handleConfirmMessage_UpdatesOrderStatus() {
        InventoryMessage inventoryMessage = new InventoryMessage(1L, 1, 1L);

        orderService.handleConfirmMessage(inventoryMessage);

        verify(orderMapper, times(1)).update(null, any());
    }

    @Test
    void handleRollbackMessage_DeletesOrderAndOrderItems() {
        InventoryMessage inventoryMessage = new InventoryMessage(1L, 1, 1L);

        orderService.handleRollbackMessage(inventoryMessage);

        verify(orderItemMapper, times(1)).delete(any());
        verify(orderMapper, times(1)).delete(any());
    }

    @Test
    void paySuccess_SendsInventoryMessages() {
        Long orderId = 1L;
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem());
        when(orderItemMapper.selectList(any())).thenReturn(orderItemList);

        orderService.paySuccess(orderId);

        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(InventoryMessage.class));
    }
}
