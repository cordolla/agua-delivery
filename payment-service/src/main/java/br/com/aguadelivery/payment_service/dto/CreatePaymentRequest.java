package br.com.aguadelivery.payment_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private String customerName;
    private String customerEmail; // E-mail é importante para o Mercado Pago
}