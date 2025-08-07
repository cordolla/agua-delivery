package br.com.aguadelivery.product_service.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(String name, BigDecimal price) {
}
