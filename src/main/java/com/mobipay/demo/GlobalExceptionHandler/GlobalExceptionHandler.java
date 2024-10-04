package com.mobipay.demo.GlobalExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {



    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



   @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleHttpClientErrorExceptionNotFound(HttpClientErrorException.NotFound ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", "The requested loan account could not be found.");
        
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Log the exception (you can use a logger here)
        // Example: logger.error("Illegal argument exception: ", ex);

        // Create a custom response
        String errorMessage = "Invalid input: " + ex.getMessage();
        
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    

   
    
}
