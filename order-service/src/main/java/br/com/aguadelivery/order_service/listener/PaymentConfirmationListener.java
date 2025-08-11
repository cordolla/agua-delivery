package br.com.aguadelivery.order_service.listener;

import br.com.aguadelivery.order_service.model.Order;
import br.com.aguadelivery.order_service.model.OrderStatus;
import br.com.aguadelivery.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentConfirmationListener {


    private final OrderRepository orderRepository;

    public PaymentConfirmationListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "payments.v1.payment-confirmed")
    @Transactional
    public void handlePaymentConfirmation(Long orderId) {
        System.out.println("Recebida confirmação de pagamento para o pedido: " + orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado para confirmação de pagamento: " + orderId));

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        System.out.println("Pedido " + orderId + " atualizado para PAGO.");
    }
}