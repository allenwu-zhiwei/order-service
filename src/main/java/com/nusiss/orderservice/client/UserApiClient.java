package com.nusiss.orderservice.client;

import com.nusiss.commonservice.entity.User;
import com.nusiss.orderservice.config.ApiResponse;
import com.nusiss.orderservice.dto.UserAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("user-service")
public interface UserApiClient {
    // 获取用户地址
    // @GetMapping("/user/inner/getUserAddress/{userId}")
    // public List<UserAddressDTO> getUserAddress(@PathVariable("userId") Long userId);
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAddressDTO>> getAddressesByUserId(@PathVariable("userId") Integer userId);

    // 获取当前用户信息
    @PostMapping("/getCurrentUserInfo")
    public ResponseEntity<ApiResponse<User>> getCurrentUserInfo(@RequestHeader("authToken") String authToken);
}
