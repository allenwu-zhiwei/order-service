package com.nusiss.orderservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nusiss.commonservice.entity.User;
import com.nusiss.orderservice.client.*;
import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.domain.Order;
import com.nusiss.orderservice.dto.CartInfoDTO;
import com.nusiss.orderservice.dto.OrderTradeDTO;
import com.nusiss.orderservice.param.SubmitOrderParam;
import com.nusiss.orderservice.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.core.Address;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author jyc
 * @Date 2024/10/11 下午11:02
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartApiClient cartApiClient;
    @Resource
    private UserApiClient userApiClient;
    @Resource
    private InventoryApiClient inventoryApiClient;

    // 去结算，获取结算页信息
    @GetMapping("/trade")
    public ApiResponse<OrderTradeDTO> getTradeInfo(HttpServletRequest request){
        // 基于gateway完成了认证添加自定义请求头
        String authToken = request.getHeader("authToken");
        if(authToken == null){
            return ApiResponse.error("用户未登录");
        }
        ResponseEntity<ApiResponse<User>> currentUserInfo = userApiClient.getCurrentUserInfo(authToken);
        User user = new User();
        if(currentUserInfo.getBody()==null){
            user = currentUserInfo.getBody().getData();
        }
        // 调用用户服务获取用户地址列表（前端没写地址，就不要了）
        ResponseEntity<List<Address>> addressesByUserId = userApiClient.getAddressesByUserId(user.getUserId());
        List<Address> userAddressDTOList = addressesByUserId.getBody();
        // 调用购物车服务获取待下单的商品信息
        List<CartInfoDTO> cartCheckedList= cartApiClient.getCartCheckedList(authToken);
        // 获取订单商品总价格
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartInfoDTO cartInfoDTO : cartCheckedList) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(cartInfoDTO.getPrice()).multiply(new BigDecimal(cartInfoDTO.getQuantity())));
        }
        OrderTradeDTO orderTradeDTO = new OrderTradeDTO();
        orderTradeDTO.setOrderDate(new Date());
        orderTradeDTO.setTotalPrice(totalPrice);
        orderTradeDTO.setCartInfoList(cartCheckedList);
        return ApiResponse.success(orderTradeDTO);
    }


    // 我的订单，获取我的订单列表
    @GetMapping("/index")
    public ApiResponse<List<Order>> index(HttpServletRequest request) {
        String authToken = request.getHeader("authToken");
        if(authToken == null){
            return ApiResponse.error("用户未登录");
        }
        ResponseEntity<ApiResponse<User>> currentUserInfo = userApiClient.getCurrentUserInfo(authToken);
        User user = new User();
        if(currentUserInfo.getBody()==null){
            user = currentUserInfo.getBody().getData();
        }
        // 获取最近十个订单
        List<Order> orderList=orderService.getOrderByUserId(user.getUserId());
        return ApiResponse.success(orderList);
    }

    // 提交订单
    @PostMapping("/submitOrder")
    public ApiResponse<Long> submitOrder(@RequestBody SubmitOrderParam submitOrderParam, HttpServletRequest request) {
        String authToken = request.getHeader("authToken");
        // 单机调试不用调用其他服务（灵言师，说出口令可跳过逻辑）
        String isTest = request.getHeader("password");
        List<CartInfoDTO> cartInfoList = submitOrderParam.getCartInfoList();
        if(isTest==null||!isTest.equals("danxindehong")) {
            // 判断库存是否足够
            for (CartInfoDTO cartInfoDTO : cartInfoList) {
                Boolean checkStock = inventoryApiClient.checkStock(cartInfoDTO.getProductId(), cartInfoDTO.getQuantity());
                if (!checkStock) {
                    return ApiResponse.error("库存不足");
                }
            }
        }
        Long orderId = orderService.submitOrder(submitOrderParam);
        // 删除购物车中已经下单的商品
        if(isTest==null||!isTest.equals("danxindehong")){
            cartApiClient.deleteCartProductsInOrder(authToken);
        }
        // 发送订单超时自动取消延迟消息（待定，提交订单同时提交支付则无该功能）
        return ApiResponse.success(orderId);
    }

    // 添加订单 测试用
    @GetMapping("/addOrder")
    public ApiResponse<Order> addOrder(){
        Order order = new Order();
        order.setUserId(1L);
        order.setOrderDate(new Date());
        order.setTotalPrice(new BigDecimal(100));
        order.setStatus("PENDING");
        order.setCreateUser("jyc");
        order.setUpdateUser("jyc");
        order.setCreateDatetime(new Date());
        order.setUpdateDatetime(new Date());
        System.out.println(order);
        int i = orderService.addOrder(order);
        System.out.println(order);
        if(i>0){
            return ApiResponse.success(order);
        }else{
            return ApiResponse.error("添加订单失败");
        }
    }
}
