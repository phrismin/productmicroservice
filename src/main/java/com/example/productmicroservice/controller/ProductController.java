package com.example.productmicroservice.controller;

import com.example.productmicroservice.service.ProductService;
import com.example.productmicroservice.service.dto.CreateProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDto createProductDto) {
        String productId = productService.createProduct(createProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
