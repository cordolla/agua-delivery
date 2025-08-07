package br.com.aguadelivery.product_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;


}