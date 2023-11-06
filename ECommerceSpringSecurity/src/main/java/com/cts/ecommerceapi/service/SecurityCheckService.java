package com.cts.ecommerceapi.service;

import com.cts.ecommerceapi.exceptions.AuthorizationException;
import com.cts.ecommerceapi.exceptions.UserNotFoundException;
import com.cts.ecommerceapi.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityCheckService {

    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;

    public Integer productValidationsCheck(HttpServletRequest request) throws AuthorizationException, UserNotFoundException {
        Integer validCheck = 0;
        if ((userRepo.findByUserName(userService.getUserName(request)) != null) || userService.getUserName(request).equals("Paranthaman")) {
            validCheck += 1;
            if(userService.getRole(request).equals("ADMIN"))
                validCheck += 1;
            else
                throw new AuthorizationException("This user does not have the authority to perform this action");
        }
        else
            throw new UserNotFoundException("Invalid User Credentials");
        return validCheck;
    }

}