package com.nusiss.orderservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.orderservice.client.CartApiClient;
import com.nusiss.orderservice.client.InventoryApiClient;
import com.nusiss.orderservice.client.UserApiClient;
import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.dto.OrderTradeDTO;
import com.nusiss.orderservice.param.SubmitOrderParam;
import com.nusiss.orderservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
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
    private UserApiClient userApiClient;

    /**
     * Test {@link OrderController#getTradeInfo(HttpServletRequest)}.
     * <p>
     * Method under test: {@link OrderController#getTradeInfo(HttpServletRequest)}
     */
    @Test
    @DisplayName("Test getTradeInfo(HttpServletRequest)")
    @Disabled("TODO: Complete this test")
    void testGetTradeInfo() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@6cbb840e testClass = com.nusiss.orderservice.controller.DiffblueFakeClass173, locations = [], classes = [com.nusiss.orderservice.controller.OrderController], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1f3264fe, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@6851061, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@e5bf22c, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@360aaab0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/trade");

        // Act
        MockMvcBuilders.standaloneSetup(orderController).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderController#getTradeInfo(HttpServletRequest)}.
     * <ul>
     *   <li>When {@link MockHttpServletRequest#MockHttpServletRequest()}.</li>
     *   <li>Then return Message is {@code 用户未登录}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#getTradeInfo(HttpServletRequest)}
     */
    @Test
    @DisplayName("Test getTradeInfo(HttpServletRequest); when MockHttpServletRequest(); then return Message is '用户未登录'")
    void testGetTradeInfo_whenMockHttpServletRequest_thenReturnMessageIs() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        OrderController orderController = new OrderController();

        // Act
        ApiResponse<OrderTradeDTO> actualTradeInfo = orderController.getTradeInfo(new MockHttpServletRequest());

        // Assert
        assertEquals("用户未登录", actualTradeInfo.getMessage());
        assertNull(actualTradeInfo.getData());
        assertFalse(actualTradeInfo.isSuccess());
    }

    /**
     * Test {@link OrderController#index(HttpServletRequest)}.
     * <p>
     * Method under test: {@link OrderController#index(HttpServletRequest)}
     */
    @Test
    @DisplayName("Test index(HttpServletRequest)")
    @Disabled("TODO: Complete this test")
    void testIndex() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@341c0f5d testClass = com.nusiss.orderservice.controller.DiffblueFakeClass175, locations = [], classes = [com.nusiss.orderservice.controller.OrderController], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1f3264fe, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@6851061, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@e5bf22c, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@360aaab0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/index");

        // Act
        MockMvcBuilders.standaloneSetup(orderController).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderController#index(HttpServletRequest)}.
     * <ul>
     *   <li>When {@link MockHttpServletRequest#MockHttpServletRequest()}.</li>
     *   <li>Then return Message is {@code 用户未登录}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#index(HttpServletRequest)}
     */
    @Test
    @DisplayName("Test index(HttpServletRequest); when MockHttpServletRequest(); then return Message is '用户未登录'")
    void testIndex_whenMockHttpServletRequest_thenReturnMessageIs() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        OrderController orderController = new OrderController();

        // Act
        ApiResponse<List<Order>> actualIndexResult = orderController.index(new MockHttpServletRequest());

        // Assert
        assertEquals("用户未登录", actualIndexResult.getMessage());
        assertNull(actualIndexResult.getData());
        assertFalse(actualIndexResult.isSuccess());
    }

    /**
     * Test
     * {@link OrderController#submitOrder(SubmitOrderParam, HttpServletRequest)}.
     * <p>
     * Method under test:
     * {@link OrderController#submitOrder(SubmitOrderParam, HttpServletRequest)}
     */
    @Test
    @DisplayName("Test submitOrder(SubmitOrderParam, HttpServletRequest)")
    @Disabled("TODO: Complete this test")
    void testSubmitOrder() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@67369975 testClass = com.nusiss.orderservice.controller.DiffblueFakeClass177, locations = [], classes = [com.nusiss.orderservice.controller.OrderController], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1f3264fe, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@6851061, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@e5bf22c, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@360aaab0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        SubmitOrderParam submitOrderParam = new SubmitOrderParam();
        submitOrderParam.setCartInfoList(new ArrayList<>());
        submitOrderParam.setTotalPrice(new BigDecimal("2.3"));
        submitOrderParam.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(submitOrderParam);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/submitOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        MockMvcBuilders.standaloneSetup(orderController).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderController#addOrder()}.
     * <ul>
     *   <li>Given {@link OrderService} {@link OrderService#addOrder(Order)} return
     * zero.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#addOrder()}
     */
    @Test
    @DisplayName("Test addOrder(); given OrderService addOrder(Order) return zero")
    void testAddOrder_givenOrderServiceAddOrderReturnZero() throws Exception {
        // Arrange
        when(orderService.addOrder(Mockito.<Order>any())).thenReturn(0);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/addOrder");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"success\":false,\"message\":\"æ·»å è®¢åå¤±è´¥\",\"data\":null}"));
    }
}
