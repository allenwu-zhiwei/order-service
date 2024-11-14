package com.nusiss.orderservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addOrder_ShouldInsertOrder() {
        // Arrange
        Order order = new Order();
        when(orderMapper.insert(order)).thenReturn(1);

        // Act
        int result = orderService.addOrder(order);

        // Assert
        assertEquals(1, result);
        verify(orderMapper, times(1)).insert(order);
    }

    @Test
    void getOrderByUserId_ShouldReturnOrderList() {
        // Arrange
        long userId = 1L;
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(new Order());
        when(orderMapper.selectList(any(QueryWrapper.class))).thenReturn(mockOrders);

        // Act
        List<Order> orders = orderService.getOrderByUserId(userId);

        // Assert
        assertEquals(1, orders.size());
        verify(orderMapper, times(1)).selectList(any(QueryWrapper.class));
    }

    @Test
    void submitOrder_ShouldCreateOrderAndItems() {
        // Arrange
        Long userId = 1L;
        BigDecimal totalPrice = BigDecimal.valueOf(100);
        List<CartInfoDTO> cartInfoList = new ArrayList<>();
        CartInfoDTO cartInfo = new CartInfoDTO();
        cartInfo.setProductId(1L);
        cartInfo.setQuantity(2);
        cartInfo.setPrice(50.0);
        cartInfoList.add(cartInfo);

        Order order = new Order();
        order.setOrderId(1L); // 这里手动设置 orderId，确保模拟返回正确的 ID。

        when(orderMapper.insert(any(Order.class))).thenAnswer(invocation -> {
            Order argOrder = invocation.getArgument(0);
            argOrder.setOrderId(1L); // 设置 orderId 模拟数据库生成的 ID。
            return 1; // 返回影响行数，假设插入成功返回1。
        });

        // Act
        Long orderId = orderService.submitOrder(userId, totalPrice, cartInfoList);

        // Assert
        assertEquals(1L, orderId);
        verify(orderMapper, times(1)).insert(any(Order.class));
        verify(orderItemMapper, times(1)).insert(any(OrderItem.class));
    }

    @Test
    void handleConfirmMessage_ShouldUpdateOrderStatusToPaid() {
        // Arrange
        InventoryMessage inventoryMessage = new InventoryMessage(1L, 2, 1L);

        when(orderMapper.update(any(), any(UpdateWrapper.class))).thenReturn(1);

        // Act
        orderService.handleConfirmMessage(inventoryMessage);

        // Assert
        verify(orderMapper, times(1)).update(any(), any(UpdateWrapper.class));
    }

    @Test
    void handleRollbackMessage_ShouldDeleteOrderAndItems() {
        // Arrange
        InventoryMessage message = new InventoryMessage(1L, 2, 1L);

        // Act
        orderService.handleRollbackMessage(message);

        // Assert
        verify(orderItemMapper, times(1)).delete(any(QueryWrapper.class));
        verify(orderMapper, times(1)).delete(any(QueryWrapper.class));
    }

    @Test
    void paySuccess_ShouldSendMessagesForOrderItems() {
        // Arrange
        Long orderId = 1L;
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setProductId(1L);
        item.setQuantity(2);
        orderItemList.add(item);

        when(orderItemMapper.selectList(any(QueryWrapper.class))).thenReturn(orderItemList);

        // Act
        orderService.paySuccess(orderId);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(eq(RabbitConfig.EXCHANGE), eq("inventory.decrement"), any(InventoryMessage.class));
    }

    @Test
    void paySuccess_ShouldHandleExceptionWhenSendingMessage() {
        // Arrange
        Long orderId = 1L;
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setProductId(1L);
        item.setQuantity(2);
        orderItemList.add(item);

        when(orderItemMapper.selectList(any(QueryWrapper.class))).thenReturn(orderItemList);
        doThrow(new RuntimeException("Message sending failed")).when(rabbitTemplate).convertAndSend(anyString(), anyString(), Optional.ofNullable(any()));

        // Act
        orderService.paySuccess(orderId);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), Optional.ofNullable(any()));
    }
}