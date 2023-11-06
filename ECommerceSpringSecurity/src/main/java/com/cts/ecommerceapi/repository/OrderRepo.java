package com.cts.ecommerceapi.repository;

import com.cts.ecommerceapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    Order findByOrderId(Integer orderId);
    Void deleteByOrderId(Integer orderId);
}
