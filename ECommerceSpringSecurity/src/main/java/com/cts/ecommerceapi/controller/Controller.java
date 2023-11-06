package com.cts.ecommerceapi.controller;

import com.cts.ecommerceapi.entity.*;
import com.cts.ecommerceapi.exceptions.*;
import com.cts.ecommerceapi.service.OrderService;
import com.cts.ecommerceapi.service.ProductService;
import com.cts.ecommerceapi.service.UserService;
import com.cts.ecommerceapi.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping(value = "/ECommerceAPI/")
public class Controller {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "AddProduct")
    @Operation(tags = {"Add Product"},summary = "This API is add a new product to the database",description = "This allows only the ADMIN to add a product. The required fields for the request are Id, Name, Price, Description and quantity",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Product.class))))
    public String addProduct(@Valid @RequestBody Product product,HttpServletRequest request) throws ProductAlreadyExists,UserNotFoundException, AuthorizationException{
        return productService.addProduct(product,request);
    }
//,responses = @ApiResponse(content = @Content(schema = @Schema(implementation = Integer.class)))
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "UpdateProduct/{productName}")
    @Operation(tags = {"Update Product"},summary = "This API can be used to update an already existing product", description = "This API allows only the ADMIN to update any product that is already available in the database. The Mandatory field is Id and the optional fields can be either price, name, description or quantity based on the requirement ")
    public String updateProduct(@RequestBody ProductDTO productDTO,@PathVariable String productName, HttpServletRequest request) throws InvalidPriceException,ProductNotFoundException,AuthorizationException, UserNotFoundException {
        return productService.updateProduct(productDTO,productName, request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = "ShowAllAvailableProducts")
    @Operation(tags = {"Display All Products"},summary = "This API displays the available products in the ECommerceAPI", description = "The available products and their quantities will be displayed")
    public List<String> ShowAllProducts(){
        return productService.showAllProducts();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = "ShowProductInfo/{productName}")
    @Operation(tags = {"Show Product Details"},summary = "This API displays information about a particular product", description = "The product's Id, name, price, quantity and description will be displayed based on the given product's name")
    public ProductDTO productInfo(@PathVariable String productName) throws ProductNotFoundException{
        return productService.productInfo(productName);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "RegisterNewUser")
    @Operation(tags = {"Register a new user"}, summary = "This API is used to add a new user", description = "This operation can be done by ADMIN or SUPER_ADMIN and the required fields to create a new user are name, emailID, password and role")
    public String registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO,HttpServletRequest request) throws UserAlreadyExistsException,AuthorizationException{
        return userService.addUser(registerUserDTO,request);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "ChangeUserDetails")
    @Operation(tags = {"Update User details"}, summary = "This API is used to change user details", description = "This operation checks the validation if the right user has logged in and allows that particular user to update his details. The mandatory field is userId and the optional fields can be name, emailId, password based on the requirements")
    public String updateUserDetails(@RequestBody UserDTO userDTO,HttpServletRequest request)throws UserNotFoundException{
        return userService.updateUserDetails(userDTO, request);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "PlaceAnOrder")
    @Operation(tags = {"Place a new order"}, summary = "This API is used to place new order", description = "This operation can be done only the particular user who has logged in through his credentials allowing him to order products. The required fields are userId , product name and quantity of that product")
    public String purchaseProduct(@Valid @RequestBody PlaceOrderRequestDto order, HttpServletRequest request) throws ProductNotFoundException, InsufficientQuantityException, UserNotFoundException {
        return orderService.placeOrder(order,request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = "GetOrderDetails/{orderId}")
    @Operation(tags = {"Display Order Details"}, summary = "This API is used to obtain info about a particular order",description = "The complete details for that particular order is displayed based on the orderId given in the path variable")
    public OrderDTO getOrderInfo(@PathVariable Integer orderId, HttpServletRequest request) throws UserNotFoundException{
        return orderService.getOrderInfo(orderId,request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = "GetAllOrders")
    public List<Order> getOrders(HttpServletRequest request){
        return orderService.getAllOrders(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = "CancelOrder/{orderId}")
    @Operation(tags = {"Cancel Order"},summary = "This API is used to cancel a particular order",description = "The orderStatus field of the orderDTO is set as CANCELLED which denotes that the particular order is cancelled")
    public String cancelOrder(@PathVariable Integer orderId, HttpServletRequest request) throws UserNotFoundException{
        return orderService.cancelOrder(orderId,request);
    }

}