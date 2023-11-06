package com.cts.ecommerceapi.service;

import com.cts.ecommerceapi.entity.*;
import com.cts.ecommerceapi.exceptions.*;
import com.cts.ecommerceapi.repository.ProductRepo;
import com.cts.ecommerceapi.dto.ProductDTO;
import com.cts.ecommerceapi.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    SecurityCheckService securityCheckService;
    public String addProduct(Product product, HttpServletRequest request) throws ProductAlreadyExists, UserNotFoundException,AuthorizationException {
        if (securityCheckService.productValidationsCheck(request) == 2) {
            Product product1 = productRepo.findByProductName(product.getProductName());
            if (product1 == null) {
                productRepo.save(product);
                return "The product is added successfully with productId: " + product.getProductId();
            } else
                throw new ProductAlreadyExists("This product is already available");
        }
        return null;
    }
    public String updateProduct(ProductDTO productDTO,String productName, HttpServletRequest request) throws InvalidPriceException, ProductNotFoundException, AuthorizationException, UserNotFoundException {
        if (securityCheckService.productValidationsCheck(request) == 2) {
            Product product = productRepo.findByProductName(productName);
            if (product != null) {
                if (productDTO.getProductName() != null)
                    product.setProductName(productDTO.getProductName());
                if (productDTO.getProductDescription() != null)
                    product.setProductDescription(productDTO.getProductDescription());
                if (productDTO.getPrice() != null)
                    if (productDTO.getPrice() > 0)
                        product.setPrice(productDTO.getPrice());
                    else
                        throw new InvalidPriceException("The price cannot be that low");
                productRepo.save(product);
                return "The product with product ID: " + product.getProductId() + " is successfully updated";
            } else
                throw new ProductNotFoundException("This product is not available");
        }
        return null;
    }

    public List<String> showAllProducts(){
        List<Product> productList = productRepo.findAll();
        List<String> productNameList = new ArrayList<>();
        for(Product product : productList){
            productNameList.add(product.getProductName());
        }
        return productNameList;
    }

    public ProductDTO productInfo(String productName) throws ProductNotFoundException{
        Product product = productRepo.findByProductName(productName);
        System.out.println(product);
        if(product != null){
            ModelMapper modelMapper = new ModelMapper();
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
        }
        else
            throw new ProductNotFoundException("The product: "+productName + " is not found");
    }
}