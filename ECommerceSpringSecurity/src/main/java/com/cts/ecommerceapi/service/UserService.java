package com.cts.ecommerceapi.service;

import com.cts.ecommerceapi.configuration.SecurityConfig;
import com.cts.ecommerceapi.dto.RegisterUserDTO;
import com.cts.ecommerceapi.entity.*;

import com.cts.ecommerceapi.exceptions.AuthorizationException;
import com.cts.ecommerceapi.exceptions.UserAlreadyExistsException;
import com.cts.ecommerceapi.exceptions.UserNotFoundException;
import com.cts.ecommerceapi.repository.OrderRepo;
import com.cts.ecommerceapi.repository.ProductRepo;
import com.cts.ecommerceapi.repository.UserRepo;
import com.cts.ecommerceapi.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;

import static com.cts.ecommerceapi.configuration.SecurityConfig.passwordEncoder;


@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    SecurityConfig securityConfig;

    public String addUser(RegisterUserDTO registerUserDTO, HttpServletRequest request) throws UserAlreadyExistsException, AuthorizationException {
        if((getRole(request).equals("ADMIN")) && (registerUserDTO.getRole().toString().equals("ADMIN")) || (getRole(request).equals("ADMIN") && registerUserDTO.getRole().toString().equals("USER"))) {
            if (checkUser(registerUserDTO).equals("valid")) {
                securityConfig.addUser(registerUserDTO.getUserName(), registerUserDTO.getPassword(), String.valueOf(registerUserDTO.getRole()));
                ModelMapper modelMapper = new ModelMapper();
                Users user = modelMapper.map(registerUserDTO,Users.class);
                user.setPassword(passwordEncoder().encode(registerUserDTO.getPassword()));
                userRepo.save(user);
                return "User with userId " + user.getUserId() + " successfully registered";
            }
        }
        else
            throw new AuthorizationException("This user does not have the authority to register user of this role");
        return null;
    }
    public String checkUser(RegisterUserDTO userDTO) throws UserAlreadyExistsException{
        Users user = userRepo.findByUserName(userDTO.getUserName());
        if (user!=null) {
            if (user.getEmailId().equals(userDTO.getEmailId()) && user.getUserName().equals(userDTO.getUserName()))
                throw new UserAlreadyExistsException("User already exists");
        }
        return "valid";
    }
    public String updateUserDetails(UserDTO userDTO,HttpServletRequest request) throws UserNotFoundException{
        Users user = userRepo.findByUserName(getUserName(request));
        String oldName = getUserName(request);
        if (user != null){
            if (userDTO.getUserName() != null)
                user.setUserName(userDTO.getUserName());
            if (userDTO.getEmailId() != null)
                user.setEmailId(userDTO.getEmailId());
            if (userDTO.getPassword() != null)
                user.setPassword(passwordEncoder().encode(userDTO.getPassword()));
            securityConfig.updateUser(oldName,user.getUserName(),user.getPassword(), String.valueOf(user.getRole()));
            userRepo.save(user);
            return "The user info is updated successfully for user with ID: "+user.getUserId();
        }
        else
            throw new UserNotFoundException("Unauthorized to perform this action");
    }

    public String getUserName(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length());
            byte[] credentials = Base64.getDecoder().decode(base64Credentials);
            String decodedCredentials = new String(credentials);
            String[] parts = decodedCredentials.split(":");
            return parts[0];
        }
        return null;
    }

    public String getRole(HttpServletRequest request) {
        String userName = getUserName(request);
        String role = securityConfig.getRole(userName);
        return role.substring(6,role.length()-1);
    }

    public String validateUser (HttpServletRequest request,Users user) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length());
            byte[] credentials = Base64.getDecoder().decode(base64Credentials);
            String decodedCredentials = new String(credentials);
            String[] parts = decodedCredentials.split(":");
            String username = parts[0];
            String password = parts[1];
            if (user.getUserName().equals(username) && BCrypt.checkpw(password,user.getPassword()))
                return "Valid";
            else
                return "Invalid";
        }
        return null;
    }
}