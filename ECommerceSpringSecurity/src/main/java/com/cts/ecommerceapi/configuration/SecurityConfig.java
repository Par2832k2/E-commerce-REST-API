package com.cts.ecommerceapi.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;


@Configuration
@Service
@EnableMethodSecurity

public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests((authorize)-> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.builder()
                .username("Paranthaman")
                .password(passwordEncoder().encode("something"))
                .roles("ADMIN")
                .build();
        inMemoryUserDetailsManager.createUser(admin);
        return  inMemoryUserDetailsManager;
    }
    public void addUser(String username, String password, String roles) {
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .roles(roles)
                .build();
        inMemoryUserDetailsManager.createUser(user);
    }

    public void updateUser(String oldName, String username, String password,String roles){
        inMemoryUserDetailsManager.deleteUser(oldName);
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .roles(roles)
                .build();
        inMemoryUserDetailsManager.createUser(user);
    }
    public String getRole(String userName){
        return inMemoryUserDetailsManager.loadUserByUsername(userName).getAuthorities().toString();
    }
}