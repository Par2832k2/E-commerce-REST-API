package com.cts.ecommerceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import javax.validation.constraints.Pattern;
//import org.hibernate.validator.constraints.Pattern;

import java.util.List;

import static com.cts.ecommerceapi.configuration.SecurityConfig.passwordEncoder;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
//    @NotNull(message = "Name cannot be null")
    private String userName;
//    @NotNull(message = "EmailId cannot be null")
    private String emailId;
//    @NotNull
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{8,20}")
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Roles role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orderList;
}