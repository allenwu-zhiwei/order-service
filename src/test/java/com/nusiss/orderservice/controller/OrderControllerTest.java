package com.nusiss.orderservice.controller;

import com.nusiss.commonservice.entity.User;
import com.nusiss.orderservice.client.CartApiClient;
import com.nusiss.orderservice.client.InventoryApiClient;
import com.nusiss.orderservice.client.PayApiClient;
import com.nusiss.orderservice.client.UserApiClient;
import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.dto.CartInfoDTO;
import com.nusiss.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private CartApiClient cartApiClient;

    @Mock
    private UserApiClient userApiClient;

    @Mock
    private InventoryApiClient inventoryApiClient;

    @Mock
    private PayApiClient paymentApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTradeInfo_ShouldReturnOrderId_WhenAllConditionsMet() {
        // Arrange
        String authToken = "validToken";
        User mockUser = new User();
        mockUser.setUserId(1);

        ResponseEntity<ApiResponse<User>> userResponse = ResponseEntity.ok(ApiResponse.success(mockUser));
        when(userApiClient.getCurrentUserInfo(authToken)).thenReturn(userResponse);

        List<CartInfoDTO> cartInfoList = new ArrayList<>();
        CartInfoDTO cartInfoDTO = new CartInfoDTO();
        cartInfoDTO.setProductId(1L);
        cartInfoDTO.setQuantity(2);
        cartInfoDTO.setPrice(50.0);
        cartInfoList.add(cartInfoDTO);

        when(cartApiClient.getCartCheckedList(authToken)).thenReturn(cartInfoList);
        when(inventoryApiClient.checkStock(1L, 2)).thenReturn(true);
        when(orderService.submitOrder(1L, BigDecimal.valueOf(100), cartInfoList)).thenReturn(1L);

        // Act
        ApiResponse<Long> response = orderController.getTradeInfo(authToken);

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getMessage());
        assertEquals(0L, response.getData());
    }

    @Test
    void getTradeInfo_ShouldReturnError_WhenAuthTokenIsNull() {
        // Act
        ApiResponse<Long> response = orderController.getTradeInfo(null);

        // Assert
        assertNotNull(response);
        assertEquals("error", response.getMessage());
        assertEquals("error", response.getMessage());
    }

    @Test
    void getTradeInfo_ShouldReturnError_WhenUserNotFound() {
        // Arrange
        String authToken = "invalidToken";
        ResponseEntity<ApiResponse<User>> userResponse = ResponseEntity.ok(ApiResponse.error("User not found"));
        when(userApiClient.getCurrentUserInfo(authToken)).thenReturn(userResponse);

        // Act
        ApiResponse<Long> response = orderController.getTradeInfo(authToken);

        // Assert
        assertNotNull(response);
        assertEquals("error", response.getMessage());
        assertEquals("error", response.getMessage());
    }

    @Test
    void getTradeInfo_ShouldReturnError_WhenStockInsufficient() {
        // Arrange
        String authToken = "validToken";
        User mockUser = new User();
        mockUser.setUserId(1);

        ResponseEntity<ApiResponse<User>> userResponse = ResponseEntity.ok(ApiResponse.success(mockUser));
        when(userApiClient.getCurrentUserInfo(authToken)).thenReturn(userResponse);

        List<CartInfoDTO> cartInfoList = new ArrayList<>();
        CartInfoDTO cartInfoDTO = new CartInfoDTO();
        cartInfoDTO.setProductId(1L);
        cartInfoDTO.setQuantity(2);
        cartInfoDTO.setPrice(50.0);
        cartInfoList.add(cartInfoDTO);

        when(cartApiClient.getCartCheckedList(authToken)).thenReturn(cartInfoList);
        when(inventoryApiClient.checkStock(1L, 2)).thenReturn(false);

        // Act
        ApiResponse<Long> response = orderController.getTradeInfo(authToken);

        // Assert
        assertNotNull(response);
        assertEquals("error", response.getMessage());
        assertEquals("error", response.getMessage());
    }

    @Test
    void index_ShouldReturnOrderList_WhenUserExists() {
        // Arrange
        String authToken = "validToken";
        User mockUser = new User();
        mockUser.setUserId(1);

        ResponseEntity<ApiResponse<User>> userResponse = ResponseEntity.ok(ApiResponse.success(mockUser));
        when(userApiClient.getCurrentUserInfo(authToken)).thenReturn(userResponse);

        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setOrderId(Long.valueOf("1"));
        orderList.add(order);

        when(orderService.getOrderByUserId(1L)).thenReturn(orderList);

        // Act
        ApiResponse<List<Order>> response = orderController.index(authToken);

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getData().size());
    }

    @Test
    void index_ShouldReturnError_WhenAuthTokenIsNull() {
        // Act
        ApiResponse<List<Order>> response = orderController.index(null);

        // Assert
        assertNotNull(response);
        assertEquals("error", response.getMessage());
        assertEquals("error", response.getMessage());
    }

    @Test
    void index_ShouldReturnError_WhenUserNotFound() {
        // Arrange
        String authToken = "invalidToken";
        ResponseEntity<ApiResponse<User>> userResponse = ResponseEntity.ok(ApiResponse.error("User not found"));
        when(userApiClient.getCurrentUserInfo(authToken)).thenReturn(userResponse);

        // Act
        ApiResponse<List<Order>> response = orderController.index(authToken);

        // Assert
        assertNotNull(response);
        assertEquals("error", response.getMessage());
        assertEquals("error", response.getMessage());
    }
}