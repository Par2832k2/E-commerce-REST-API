package com.cts.ecommerceapi.dto;

import com.cts.ecommerceapi.entity.Order;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    @Min(value = 1,message = "Price should be atleast more than 1")
    private Integer price;
    private String productName;
    private String productDescription;
    @Min(value = 1,message = "Enter a valid quantity")
    private int quantity;
}
