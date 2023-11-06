package com.cts.ecommerceapi.repository;

import com.cts.ecommerceapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    Product findByProductId(Integer productId);
    Product findByProductName(String productName);

}
