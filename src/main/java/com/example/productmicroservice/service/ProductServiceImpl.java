package com.example.productmicroservice.service;

import com.example.productmicroservice.service.dto.CreateProductDto;
import com.example.productmicroservice.service.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public String createProduct(CreateProductDto dto) {
        // TODO save to DB
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent event = new ProductCreatedEvent(productId, dto.getTitle(), dto.getPrice(), dto.getQuantity());
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate
                .send("product-created-events-topic", productId, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                LOGGER.error("Failed to send message: {}", ex.getMessage());
            } else {
                LOGGER.info("Message sent successfully: {}", result.getRecordMetadata());
            }
        });

        LOGGER.info("Return: {}", productId);
        return productId;
    }
}
