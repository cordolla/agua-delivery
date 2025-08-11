package br.com.aguadelivery.payment_service.dto;

import br.com.aguadelivery.payment_service.model.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponse {
    private Long id;
    private PaymentStatus status;
    private String qrCodeBase64;
    private String qrCodeCopyPaste;
}