package br.com.aguadelivery.payment_service.repository;

import br.com.aguadelivery.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByMercadoPagoPaymentId(Long mercadoPagoPaymentId);
}
