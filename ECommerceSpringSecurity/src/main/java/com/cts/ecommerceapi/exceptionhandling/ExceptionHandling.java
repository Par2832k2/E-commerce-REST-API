package com.cts.ecommerceapi.exceptionhandling;

import com.cts.ecommerceapi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(InvalidPriceException.class)
    public String invalidPriceException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(InvalidProductNameException.class)
    public String invalidProductNameException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public String productNotFoundException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(ProductAlreadyExists.class)
    public String productAlreadyExists(Exception e){return e.getMessage();}
    @ExceptionHandler(InsufficientQuantityException.class)
    public String insufficientQuantityException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String userAlreadyExistsException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public String authenticationException(org.springframework.security.core.AuthenticationException e){
        return "e.getMessage()";
    }
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedException(Exception e){
        return "The user does not have the authority to perform this action";
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String returnException(MethodArgumentNotValidException e){
        List<Object>objectList = (List<Object>) Objects.requireNonNull(e.getDetailMessageArguments())[1];
        String[] message = ((String)objectList.get(0)).split(":");
        return message[1];
    }
}

