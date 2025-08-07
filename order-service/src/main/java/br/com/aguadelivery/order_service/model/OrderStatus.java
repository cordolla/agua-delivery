package br.com.aguadelivery.order_service.model;

public enum OrderStatus {
    PENDING_PAYMENT, // Aguardando Pagamento
    PAID,            // Pago
    SHIPPED,         // Enviado
    DELIVERED,       // Entregue
    CANCELED,        // Cancelado
    FAILED           // Falhou
}
