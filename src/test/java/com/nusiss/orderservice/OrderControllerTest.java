// package com.nusiss.orderservice;
//
// import java.util.*;
// import java.math.*;
//
// import com.nusiss.commonservice.entity.User;
// import com.nusiss.orderservice.client.*;
// import com.nusiss.orderservice.config.ApiResponse;
// import com.nusiss.orderservice.controller.OrderController;
// import com.nusiss.orderservice.domain.Order;
// import com.nusiss.orderservice.dto.CartInfoDTO;
// import com.nusiss.orderservice.dto.OrderTradeDTO;
// import com.nusiss.orderservice.param.SubmitOrderParam;
// import com.nusiss.orderservice.service.OrderService;
// import jakarta.servlet.http.HttpServletRequest;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
// import java.math.BigDecimal;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
//
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
// @ExtendWith(MockitoExtension.class)
// public class OrderControllerTest {
//
//     private MockMvc mockMvc;
//
//     @Mock
//     private OrderService orderService;
//
//     @Mock
//     private CartApiClient cartApiClient;
//
//     @Mock
//     private UserApiClient userApiClient;
//
//     @Mock
//     private InventoryApiClient inventoryApiClient;
//
//     @InjectMocks
//     private OrderController orderController;
//
//     @BeforeEach
//     public void setUp() {
//         mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
//     }
//
//     @Test
//     public void getTradeInfo_UserNotLoggedIn_ReturnsError() throws Exception {
//         mockMvc.perform(get("/order/trade"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.code").value(400))
//                 .andExpect(jsonPath("$.message").value("用户未登录"));
//     }
//
//     @Test
//     public void getTradeInfo_UserLoggedIn_ReturnsOrderTradeDTO() throws Exception {
//         String authToken = "validToken";
//         User user = new User();
//         user.setUserId(1);
//
//         ResponseEntity<ApiResponse<User>> currentUserInfoResponse = ResponseEntity.ok(ApiResponse.success(user));
//         when(userApiClient.getCurrentUserInfo(authToken)).thenReturn(currentUserInfoResponse);
//
//         List<CartInfoDTO> cartCheckedList = new ArrayList<>();
//         CartInfoDTO cartInfoDTO = new CartInfoDTO();
//         cartInfoDTO.setPrice(100);
//         cartInfoDTO.setQuantity(2);
//         cartCheckedList.add(cartInfoDTO);
//
//         when(cartApiClient.getCartCheckedList(authToken)).thenReturn(cartCheckedList);
//
//         mockMvc.perform(get("/order/trade").header("authToken", authToken))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.code").value(200))
//                 .andExpect(jsonPath("$.data.totalPrice").value(200));
//     }
//
//     @Test
//     public void index_UserNotLoggedIn_ReturnsError() throws Exception {
//         mockMvc.perform(get("/order/index"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.code").value(400))
//                 .andExpect(jsonPath("$.message").value("用户未登录"));
//     }
//
//     @Test
//     public void index_UserLoggedIn_ReturnsOrderList() throws Exception {
//         String authToken = "validToken";
//         User user = new User();
//         user.setUserId(1);
//
//         ResponseEntity<ApiResponse<User>> currentUserInfoResponse = ResponseEntity.ok(ApiResponse.success(user));
//         when(userApiClient.getCurrentUserInfo(authToken)).thenReturn(currentUserInfoResponse);
//
//         List<Order> orderList = new ArrayList<>();
//         Order order = new Order();
//         order.setUserId(1L);
//         order.setOrderDate(new Date());
//         order.setTotalPrice(new BigDecimal(100));
//         order.setStatus("PENDING");
//         order.setCreateUser("jyc");
//         order.setUpdateUser("jyc");
//         order.setCreateDatetime(new Date());
//         order.setUpdateDatetime(new Date());
//         orderList.add(order);
//
//         when(orderService.getOrderByUserId(user.getUserId())).thenReturn(orderList);
//
//         mockMvc.perform(get("/order/index").header("authToken", authToken))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.code").value(200))
//                 .andExpect(jsonPath("$.data[0].totalPrice").value(100));
//     }
//
//     @Test
//     public void submitOrder_InsufficientStock_ReturnsError() throws Exception {
//         String authToken = "validToken";
//         SubmitOrderParam submitOrderParam = new SubmitOrderParam();
//         List<CartInfoDTO> cartInfoList = new ArrayList<>();
//         CartInfoDTO cartInfoDTO = new CartInfoDTO();
//         cartInfoDTO.setProductId(1L);
//         cartInfoDTO.setQuantity(2);
//         cartInfoList.add(cartInfoDTO);
//         submitOrderParam.setCartInfoList(cartInfoList);
//
//         when(inventoryApiClient.checkStock(anyLong(), anyInt())).thenReturn(false);
//
//         mockMvc.perform(post("/order/submitOrder")
//                         .header("authToken", authToken)
//                         .contentType("application/json")
//                         .content("{\"cartInfoList\":[{\"productId\":1,\"quantity\":2}]}"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.code").value(400))
//                 .andExpect(jsonPath("$.message").value("库存不足"));
//     }
//
//     @Test
//     public void submitOrder_SuccessfulOrderPlacement() throws Exception {
//         String authToken = "validToken";
//         SubmitOrderParam submitOrderParam = new SubmitOrderParam();
//         List<CartInfoDTO> cartInfoList = new ArrayList<>();
//         CartInfoDTO cartInfoDTO = new CartInfoDTO();
//         cartInfoDTO.setProductId(1L);
//         cartInfoDTO.setQuantity(2);
//         cartInfoList.add(cartInfoDTO);
//         submitOrderParam.setCartInfoList(cartInfoList);
//
//         when(inventoryApiClient.checkStock(anyLong(), anyInt())).thenReturn(true);
//         when(orderService.submitOrder(any(SubmitOrderParam.class))).thenReturn(1L);
//
//         mockMvc.perform(post("/order/submitOrder")
//                         .header("authToken", authToken)
//                         .contentType("application/json")
//                         .content("{\"cartInfoList\":[{\"productId\":1,\"quantity\":2}]}"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.code").value(200))
//                 .andExpect(jsonPath("$.data").value(1L));
//     }
//
//     @Test
//     public void addOrder_SuccessfulOrderAddition() throws Exception {
//         Order order = new Order();
//         order.setUserId(1L);
//         order.setOrderDate(new Date());
//         order.setTotalPrice(new BigDecimal(100));
//         order.setStatus("PENDING");
//         order.setCreateUser("jyc");
//         order.setUpdateUser("jyc");
//         order.setCreateDatetime(new Date());
//         order.setUpdateDatetime(new Date());
//
//         when(orderService.addOrder(any(Order.class))).thenReturn(1);
//
//         mockMvc.perform(get("/order/addOrder"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.code").value(200))
//                 .andExpect(jsonPath("$.data.totalPrice").value(100));
//     }
// }
