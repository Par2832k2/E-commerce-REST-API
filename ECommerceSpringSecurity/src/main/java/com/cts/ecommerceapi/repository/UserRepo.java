package com.cts.ecommerceapi.repository;

import com.cts.ecommerceapi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {
    Users findByUserName(String userName);
    Users findByEmailId(String emailId);
    Users findByUserId(Integer userID);
}
