package com.nusiss.orderservice.param;

import com.nusiss.orderservice.dto.CartInfoDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author jyc
 * @Date 2024/10/13 上午2:04
 */
@Data
public class SubmitOrderParam {
    private Long userId;
    private BigDecimal totalPrice;
    private List<CartInfoDTO> cartInfoList;
}
