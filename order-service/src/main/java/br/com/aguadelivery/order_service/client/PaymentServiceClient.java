package br.com.aguadelivery.order_service.client;

import br.com.aguadelivery.order_service.client.dtos.CreatePaymentRequest;
import br.com.aguadelivery.order_service.client.dtos.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @PostMapping("/api/payments")
    PaymentResponse createPayment(@RequestBody CreatePaymentRequest request);
}