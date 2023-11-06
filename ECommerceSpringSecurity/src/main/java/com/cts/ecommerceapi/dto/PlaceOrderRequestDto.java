package com.cts.ecommerceapi.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequestDto {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer orderId;
//    @NotNull
//    private int userId;
    @NotBlank(message = "Product name cannot be null or blank")
    private String productName;
    @Min(value = 1,message = "Please enter a valid quantity")
    @NotNull(message = "Product quantity cannot be null or blank")
    private int quantity;
}
