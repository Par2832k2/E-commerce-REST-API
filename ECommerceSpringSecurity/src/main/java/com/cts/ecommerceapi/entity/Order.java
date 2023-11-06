package com.cts.ecommerceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NotNull(message = "Id cannot be null")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    private Integer totalCost;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "Product_ID", referencedColumnName = "productId")
    @JsonIgnore
    private Product product;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_ID", referencedColumnName = "userId")
    @JsonIgnore
    private Users user;
}