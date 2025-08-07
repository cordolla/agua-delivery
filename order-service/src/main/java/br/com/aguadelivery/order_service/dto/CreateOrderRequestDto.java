package br.com.aguadelivery.order_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDto {

    private String customerName;
    private String whatsappNumber;
    private String shippingAddress;

    private List<OrderItemRequestDto> items;

    @Data
    public static class OrderItemRequestDto {
        private Long productId;
        private Integer quantity;
    }
}
