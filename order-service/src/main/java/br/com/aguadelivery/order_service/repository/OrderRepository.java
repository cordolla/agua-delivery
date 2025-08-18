package br.com.aguadelivery.order_service.repository;

import br.com.aguadelivery.order_service.model.Order;
import br.com.aguadelivery.order_service.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
