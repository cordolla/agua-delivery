package br.com.aguadelivery.payment_service.controller;

import br.com.aguadelivery.payment_service.dto.CreatePaymentRequest;
import br.com.aguadelivery.payment_service.dto.PaymentResponse;
import br.com.aguadelivery.payment_service.model.Payment;
import br.com.aguadelivery.payment_service.service.PaymentService;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody CreatePaymentRequest request) {
        try {
            Payment payment = paymentService.createPixPayment(request);
            PaymentResponse response = new PaymentResponse();
            response.setId(payment.getId());
            response.setStatus(payment.getStatus());
            response.setQrCodeBase64(payment.getQrCodeBase64());
            response.setQrCodeCopyPaste(payment.getQrCodeCopyPaste());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (MPException e) {
            System.err.println("Erro na API do Mercado Pago: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Erro ao se comunicar com o provedor de pagamento."));
        } catch (Exception e) {
            System.err.println("Erro inesperado no payment-service: ");
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocorreu um erro interno no serviço de pagamento."));
        }
    }

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.OK)
    public void handleWebhook(@RequestParam("data.id") String paymentId) {
        System.out.println("Webhook de pagamento recebido: " + paymentId);
        try {
            Long orderId = paymentService.confirmPayment(Long.parseLong(paymentId));
            paymentService.notifyOrderService(orderId);
        } catch (Exception e) {
            System.err.println("Falha ao processar webhook para o pagamento " + paymentId);
            e.printStackTrace();
        }
    }
}