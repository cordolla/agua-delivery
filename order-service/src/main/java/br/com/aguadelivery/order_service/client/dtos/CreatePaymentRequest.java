package br.com.aguadelivery.order_service.client.dtos;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CreatePaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private String customerName;
    private String customerEmail;
}