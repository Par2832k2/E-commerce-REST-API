package com.cts.ecommerceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    @Min(value = 1,message = "Price cannot be lower than 1")
    @NotNull(message = "Price cannot be null")
    private Integer price;
    @NotNull(message = "Product Name cannot be null")
    @NotBlank(message = "Product Name cannot be null or blank")
    private String productName;
    @NotNull(message = "Product Description cannot be null")
    @NotBlank(message = "Product Description cannot be null or blank")
    private String productDescription;
    @Min(value = 0, message = "Quantity must be atleast 1")
    @NotNull(message = "Product quantity must not be null")
    private Integer quantity;
    @OneToMany
    @JsonIgnore
    private List<Order> orderList;
}
