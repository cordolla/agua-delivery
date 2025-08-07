package br.com.aguadelivery.order_service.repository;

import br.com.aguadelivery.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
