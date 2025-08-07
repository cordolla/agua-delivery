package br.com.aguadelivery.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtTimeOfPurchase;
    private BigDecimal subtotal;

}
