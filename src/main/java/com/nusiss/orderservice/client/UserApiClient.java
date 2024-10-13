package com.nusiss.orderservice.client;

import com.nusiss.orderservice.dto.UserAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("user-service")
public interface UserApiClient {
    //获取用户地址
    @GetMapping("/user/inner/getUserAddress/{userId}")
    public List<UserAddressDTO> getUserAddress(@PathVariable("userId") Long userId);
}
