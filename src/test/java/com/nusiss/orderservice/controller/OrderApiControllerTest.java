package com.nusiss.orderservice.controller;

import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderApiControllerTest {

    @InjectMocks
    private OrderApiController orderApiController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void paySuccess_ShouldReturnSuccessResponse() {
        // Arrange
        Long orderId = 1L;
        doNothing().when(orderService).paySuccess(orderId);

        // Act
        ApiResponse response = orderApiController.paySuccess(orderId);

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getMessage());
        verify(orderService, times(1)).paySuccess(orderId);
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenOrderExists() {
        // Arrange
        String orderId = "12345";
        Order mockOrder = new Order();
        mockOrder.setOrderId(Long.valueOf(orderId));
        when(orderService.getById(orderId)).thenReturn(mockOrder);

        // Act
        Order response = orderApiController.getOrderById(orderId);

        // Assert
        assertNotNull(response);
        assertEquals(Long.valueOf(orderId), response.getOrderId());
        verify(orderService, times(1)).getById(orderId);
    }

    @Test
    void getOrderById_ShouldReturnNull_WhenOrderDoesNotExist() {
        // Arrange
        String orderId = "non-existent-id";
        when(orderService.getById(orderId)).thenReturn(null);

        // Act
        Order response = orderApiController.getOrderById(orderId);

        // Assert
        assertNull(response);
        verify(orderService, times(1)).getById(orderId);
    }
}