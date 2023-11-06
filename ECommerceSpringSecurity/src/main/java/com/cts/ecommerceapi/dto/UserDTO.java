package com.cts.ecommerceapi.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
//    @NotNull(message = "id cannot be null")
//    @Id
//    private Integer userId;
    private String userName;
    @Email(message = "EmailId seems to be invalid")
    private String emailId;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{8,20}",message = "Invalid password. Password can be from 8 - 20 characters in length , it must contain atleast 1 number, atleast 1 special character ")
    private String password;
}