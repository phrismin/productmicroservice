package com.example.productmicroservice.service;

import com.example.productmicroservice.service.dto.CreateProductDto;

public interface ProductService {
    String createProduct(CreateProductDto dto);
}
