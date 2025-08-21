package br.com.aguadelivery.payment_service.service;

import br.com.aguadelivery.payment_service.config.AppConfig;
import br.com.aguadelivery.payment_service.dto.CreatePaymentRequest;
import br.com.aguadelivery.payment_service.model.Payment;
import br.com.aguadelivery.payment_service.model.PaymentStatus;
import br.com.aguadelivery.payment_service.repository.PaymentRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;

    public PaymentService(PaymentRepository paymentRepository, RabbitTemplate rabbitTemplate) {
        this.paymentRepository = paymentRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Payment createPixPayment(CreatePaymentRequest request) throws MPException, MPApiException {
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PENDING);

        PaymentClient client = new PaymentClient();
        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(request.getAmount())
                .description("Pedido #" + request.getOrderId())
                .paymentMethodId("pix")
                .payer(PaymentPayerRequest.builder().email(request.getCustomerEmail()).firstName(request.getCustomerName()).build())
                .notificationUrl("https://466dde853e38.ngrok-free.app/api/payments/webhook")
                .dateOfExpiration(OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(30))
                .build();

        com.mercadopago.resources.payment.Payment mpPayment = client.create(createRequest);

        payment.setMercadoPagoPaymentId(mpPayment.getId());

        if (mpPayment.getPointOfInteraction() != null && mpPayment.getPointOfInteraction().getTransactionData() != null) {
            payment.setQrCodeBase64(mpPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64());
            payment.setQrCodeCopyPaste(mpPayment.getPointOfInteraction().getTransactionData().getQrCode());
        }

        return paymentRepository.save(payment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long confirmPayment(Long mpPaymentId) {
        System.out.println("Buscando pagamento com ID do Mercado Pago: " + mpPaymentId);
        Payment payment = paymentRepository.findByMercadoPagoPaymentId(mpPaymentId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado: " + mpPaymentId));

        if (payment.getStatus() == PaymentStatus.PENDING) {
            payment.setStatus(PaymentStatus.APPROVED);
            paymentRepository.save(payment);
            System.out.println("Pagamento " + payment.getId() + " atualizado para APPROVED no banco de dados.");
            return payment.getOrderId();
        }
        return null;
    }

    public void notifyOrderService(Long orderId) {
        if (orderId != null) {
            System.out.println("Enviando notificação para o RabbitMQ para o pedido ID: " + orderId);
            rabbitTemplate.convertAndSend(AppConfig.QUEUE_PAYMENT_CONFIRMED, orderId);
            System.out.println("Notificação enviada com sucesso.");
        }
    }
}