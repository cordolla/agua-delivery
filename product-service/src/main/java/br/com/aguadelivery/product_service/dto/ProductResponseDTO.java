package br.com.aguadelivery.product_service.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String name, BigDecimal price) {
}
