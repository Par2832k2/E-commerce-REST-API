package com.cts.ecommerceapi.dto;

import com.cts.ecommerceapi.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private Integer totalCost;
    //private Integer userId;
    private String userName;
    private Integer productId;
    private String productName;
    private Integer productQuantity;
    private OrderStatus orderStatus;
}
