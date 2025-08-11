package br.com.aguadelivery.order_service.dto;

import br.com.aguadelivery.order_service.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private String customerName;
    private String whatsappNumber;
    private String shippingAddress;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDto> items;

    private String qrCodeBase64;
    private String qrCodeCopyPaste;
}
