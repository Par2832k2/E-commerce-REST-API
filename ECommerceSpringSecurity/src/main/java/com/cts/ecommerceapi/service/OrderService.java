package com.cts.ecommerceapi.service;

import com.cts.ecommerceapi.entity.Order;
import com.cts.ecommerceapi.entity.OrderStatus;
import com.cts.ecommerceapi.entity.Product;
import com.cts.ecommerceapi.entity.Users;
import com.cts.ecommerceapi.exceptions.InsufficientQuantityException;
import com.cts.ecommerceapi.exceptions.ProductNotFoundException;
import com.cts.ecommerceapi.exceptions.UserNotFoundException;
import com.cts.ecommerceapi.repository.OrderRepo;
import com.cts.ecommerceapi.repository.ProductRepo;
import com.cts.ecommerceapi.repository.UserRepo;
import com.cts.ecommerceapi.dto.OrderDTO;
import com.cts.ecommerceapi.dto.PlaceOrderRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    public String placeOrder(PlaceOrderRequestDto orderDto, HttpServletRequest request) throws ProductNotFoundException, InsufficientQuantityException, UserNotFoundException {
        Users user = userRepo.findByUserName(userService.getUserName(request));
        if (user != null) {
            Product product = productRepo.findByProductName(orderDto.getProductName());
            if (product != null) {
                if (orderDto.getQuantity() <= product.getQuantity()) {
                    Order order = new Order();
                    order.setTotalCost(product.getPrice() * orderDto.getQuantity());
                    product.setQuantity((product.getQuantity() - orderDto.getQuantity()));
                    productRepo.save(product);
                    order.setProduct(product);
                    order.setUser(user);
                    order.setOrderStatus(OrderStatus.ACTIVE);
                    orderRepo.save(order);
                    return "The order with orderId " + order.getOrderId() + " is successfully registered";
                } else
                    throw new InsufficientQuantityException("Insufficient Stock. There are only " + product.getQuantity() + " no of products available");
            } else
                throw new ProductNotFoundException("The product " + orderDto.getProductName() + " is not available");
        } else
            throw new UserNotFoundException("This User does not exist");
    }

    public List<Order> getAllOrders(HttpServletRequest request){
        ModelMapper modelMapper = new ModelMapper();
        List<OrderDTO>orderDTOList = new ArrayList<>();
        String userName = userService.getUserName(request);
        Users user = userRepo.findByUserName(userName);
        List<Order>orderList = user.getOrderList();
        for(Order order : orderList){
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTOList.add(orderDTO);
        }
        return orderList;
    }
    public OrderDTO getOrderInfo(Integer orderId,HttpServletRequest request) throws UserNotFoundException {
        Order order = orderRepo.findByOrderId(orderId);
        Users user = userRepo.findByUserId(order.getUser().getUserId());
        if ((userService.validateUser(request, user)).equals("Valid")) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setTotalCost(order.getTotalCost());
            //orderDTO.setUserId(order.getUser().getUserId());
            orderDTO.setUserName(order.getUser().getUserName());
            orderDTO.setProductId(order.getProduct().getProductId());
            orderDTO.setProductName(order.getProduct().getProductName());
            Integer quantity = order.getTotalCost() / (order.getProduct().getPrice());
            orderDTO.setProductQuantity(quantity);
            orderDTO.setOrderStatus(order.getOrderStatus());
            return orderDTO;
        } else
            throw new UserNotFoundException("Unauthorized to perform this action");
    }
    public String cancelOrder(Integer orderId,HttpServletRequest request) throws UserNotFoundException {
        Order order = orderRepo.findByOrderId(orderId);
        Users user = userRepo.findByUserId(order.getUser().getUserId());
        if(order.getOrderStatus().equals(OrderStatus.CANCELLED))
            return "Your order has been cancelled already";
        if ((userService.validateUser(request, user)).equals("Valid")) {
            Product product = order.getProduct();
            Integer quantity = (order.getTotalCost() / product.getPrice());
            product.setQuantity(product.getQuantity() + quantity);
            productRepo.save(product);
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepo.save(order);
            //orderRepo.delete(order);
            //orderRepo.deleteByOrderId(orderId);
            return "Your Order has been cancelled";
        } else
            throw new UserNotFoundException("Unauthorized to perform this action");
    }
}
