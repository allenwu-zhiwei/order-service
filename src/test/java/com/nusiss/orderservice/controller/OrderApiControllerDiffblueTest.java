package com.nusiss.orderservice.controller;

import static org.mockito.Mockito.when;

import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.service.OrderService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderApiController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderApiControllerDiffblueTest {
    @Autowired
    private OrderApiController orderApiController;

    @MockBean
    private OrderService orderService;

    /**
     * Test {@link OrderApiController#getOrderById(String)}.
     * <p>
     * Method under test: {@link OrderApiController#getOrderById(String)}
     */
    @Test
    @DisplayName("Test getOrderById(String)")
    void testGetOrderById() throws Exception {
        // Arrange
        Order order = new Order();
        order.setCreateDatetime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order.setCreateUser("Create User");
        order.setOrderDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order.setOrderId(1L);
        order.setStatus("Status");
        order.setTotalPrice(new BigDecimal("2.3"));
        order.setUpdateDatetime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order.setUpdateUser("2020-03-01");
        order.setUserId(1L);
        when(orderService.getById(Mockito.<Serializable>any())).thenReturn(order);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/inner/getOrderInfo/{orderId}",
                "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":1,\"userId\":1,\"orderDate\":0,\"totalPrice\":2.3,\"status\":\"Status\",\"createUser\":\"Create"
                                        + " User\",\"updateUser\":\"2020-03-01\",\"createDatetime\":0,\"updateDatetime\":0}"));
    }

    /**
     * Test {@link OrderApiController#paySuccess(Long)}.
     * <p>
     * Method under test: {@link OrderApiController#paySuccess(Long)}
     */
    @Test
    @DisplayName("Test paySuccess(Long)")
    @Disabled("TODO: Complete this test")
    void testPaySuccess() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/order/inner/paySuccess");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("orderId", String.valueOf(1L));

        // Act
        MockMvcBuilders.standaloneSetup(orderApiController).build().perform(requestBuilder);
    }
}
