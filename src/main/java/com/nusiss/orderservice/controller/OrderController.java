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
import java.util.Objects;

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
    @Resource
    private PayApiClient paymentApiClient;

    // 去结算，提交订单(前端没有界面，二合一了)
    @GetMapping("/checkout")
    public ApiResponse<OrderTradeDTO> getTradeInfo(@RequestHeader("authToken") String authToken){
        // 基于gateway完成了认证添加自定义请求头
        if(authToken == null){
            return ApiResponse.error("User haven't login");
        }
        ResponseEntity<ApiResponse<User>> currentUserInfo = userApiClient.getCurrentUserInfo(authToken);
        User user = new User();
        if(Objects.requireNonNull(currentUserInfo.getBody()).getData()!=null){
            user = currentUserInfo.getBody().getData();
        }else {
            return ApiResponse.error("未查询到用户");
        }
        // 调用用户服务获取用户地址列表（前端没写地址，就不要了）
        // ResponseEntity<List<Address>> addressesByUserId = userApiClient.getAddressesByUserId(user.getUserId());
        // List<Address> userAddressDTOList = addressesByUserId.getBody();
        // 调用购物车服务获取待下单的商品信息
        List<CartInfoDTO> cartInfoList= cartApiClient.getCartCheckedList(authToken);
        // 获取订单商品总价格
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartInfoDTO cartInfoDTO : cartInfoList) {
            Boolean checkStock = inventoryApiClient.checkStock(cartInfoDTO.getProductId(), cartInfoDTO.getQuantity());
            if (!checkStock) {
                return ApiResponse.error("库存不足");
            }
            totalPrice = totalPrice.add(BigDecimal.valueOf(cartInfoDTO.getPrice()).multiply(new BigDecimal(cartInfoDTO.getQuantity())));
        }
        Long orderId = orderService.submitOrder(Long.valueOf(user.getUserId()), totalPrice, cartInfoList);
        // 删除购物车中已经下单的商品
        cartApiClient.deleteCartProductsInOrder(authToken);
        // 同时提交支付(因为前端没有支付页面)
        paymentApiClient.submitPayment(orderId.toString());
        return ApiResponse.success();
    }


    // 我的订单，获取我的订单列表
    @GetMapping("/index")
    public ApiResponse<List<Order>> index(@RequestHeader("authToken") String authToken) {
        if(authToken == null){
            return ApiResponse.error("用户未登录");
        }
        ResponseEntity<ApiResponse<User>> currentUserInfo = userApiClient.getCurrentUserInfo(authToken);
        User user = new User();
        if(Objects.requireNonNull(currentUserInfo.getBody()).getData()!=null){
            user = currentUserInfo.getBody().getData();
        }else {
            return ApiResponse.error("未查询到用户");
        }
        // 获取最近十个订单
        List<Order> orderList=orderService.getOrderByUserId(user.getUserId());
        return ApiResponse.success(orderList);
    }

    // // 提交订单
    // @PostMapping("/submitOrder")
    // public ApiResponse<Long> submitOrder(@RequestBody SubmitOrderParam submitOrderParam, @RequestHeader("authToken") String authToken) {
    //     List<CartInfoDTO> cartInfoList = submitOrderParam.getCartInfoList();
    //     // 判断库存是否足够
    //     for (CartInfoDTO cartInfoDTO : cartInfoList) {
    //         Boolean checkStock = inventoryApiClient.checkStock(cartInfoDTO.getProductId(), cartInfoDTO.getQuantity());
    //         if (!checkStock) {
    //             return ApiResponse.error("库存不足");
    //         }
    //     }
    //     Long orderId = orderService.submitOrder(submitOrderParam);
    //     // 删除购物车中已经下单的商品
    //     cartApiClient.deleteCartProductsInOrder(authToken);
    //     // 发送订单超时自动取消延迟消息（待定，提交订单同时提交支付则无该功能）
    //     return ApiResponse.success(orderId);
    // }

    // // 添加订单 测试用
    // @GetMapping("/addOrder")
    // public ApiResponse<Order> addOrder(){
    //     Order order = new Order();
    //     order.setUserId(1L);
    //     order.setOrderDate(new Date());
    //     order.setTotalPrice(new BigDecimal(100));
    //     order.setStatus("PENDING");
    //     order.setCreateUser("jyc");
    //     order.setUpdateUser("jyc");
    //     order.setCreateDatetime(new Date());
    //     order.setUpdateDatetime(new Date());
    //     System.out.println(order);
    //     int i = orderService.addOrder(order);
    //     System.out.println(order);
    //     if(i>0){
    //         return ApiResponse.success(order);
    //     }else{
    //         return ApiResponse.error("添加订单失败");
    //     }
    // }
}
