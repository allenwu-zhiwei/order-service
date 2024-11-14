package com.nusiss.orderservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.nusiss.commonservice.entity.User;
import com.nusiss.orderservice.client.CartApiClient;
import com.nusiss.orderservice.client.InventoryApiClient;
import com.nusiss.orderservice.client.PayApiClient;
import com.nusiss.orderservice.client.UserApiClient;
import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.service.OrderService;

import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderControllerDiffblueTest {
    @MockBean
    private CartApiClient cartApiClient;

    @MockBean
    private InventoryApiClient inventoryApiClient;

    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PayApiClient payApiClient;

    @MockBean
    private UserApiClient userApiClient;

    /**
     * Test {@link OrderController#getTradeInfo(String)}.
     * <p>
     * Method under test: {@link OrderController#getTradeInfo(String)}
     */
    @Test
    @DisplayName("Test getTradeInfo(String)")
    void testGetTradeInfo() throws Exception {
        // Arrange
        ApiResponse<User> successResult = ApiResponse.success();
        when(userApiClient.getCurrentUserInfo(Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(successResult, HttpStatusCode.valueOf(200)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/checkout")
                .header("authToken", "ABC123");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"success\":false,\"message\":\"æªæ¥è¯¢å°ç¨æ·\",\"data\":null}"));
    }

    /**
     * Test {@link OrderController#index(String)}.
     * <p>
     * Method under test: {@link OrderController#index(String)}
     */
    @Test
    @DisplayName("Test index(String)")
    @Disabled("TODO: Complete this test")
    void testIndex() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/index")
                .header("authToken", "ABC123");

        // Act
        MockMvcBuilders.standaloneSetup(orderController).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderController#index(String)}.
     * <ul>
     *   <li>When {@code null}.</li>
     *   <li>Then return Message is {@code 用户未登录}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#index(String)}
     */
    @Test
    @DisplayName("Test index(String); when 'null'; then return Message is '用户未登录'")
    void testIndex_whenNull_thenReturnMessageIs() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange and Act
        ApiResponse<List<Order>> actualIndexResult = (new OrderController()).index(null);

        // Assert
        assertEquals("用户未登录", actualIndexResult.getMessage());
        assertNull(actualIndexResult.getData());
        assertFalse(actualIndexResult.isSuccess());
    }
}
