package com.cts.ecommerceapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "E-CommerceAPI", description = "An Rest API allowing options for a user to register place order and purchase products."),
        servers = {@Server(url = "http://localhost:8080",description = "This is the local host")}
)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
