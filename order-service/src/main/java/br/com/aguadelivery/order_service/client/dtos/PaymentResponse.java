package br.com.aguadelivery.order_service.client.dtos;

import lombok.Data;

@Data
public class PaymentResponse {
    private String qrCodeBase64;
    private String qrCodeCopyPaste;
}