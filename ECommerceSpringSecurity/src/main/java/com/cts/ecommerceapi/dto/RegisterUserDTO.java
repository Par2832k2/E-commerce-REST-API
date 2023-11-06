package com.cts.ecommerceapi.dto;

import com.cts.ecommerceapi.entity.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    @NotNull(message = "User Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9].{6,15}$", message = "The user name can be from 6 - 15 characters in length")
    private String userName;
    @NotNull(message = "EmailId cannot be null")
    @Email(message = "EmailId seems to be invalid")
    private String emailId;
    @NotNull(message =  "Password cannot be null")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{8,20}",message = "Invalid password. Password can be from 8 - 20 characters in length , it must contain atleast 1 number, atleast 1 special character ")
    private String password;
    @NotNull(message = "Role can be either User or Admin Only")
    @Enumerated(EnumType.STRING)
    private Roles role;
}
