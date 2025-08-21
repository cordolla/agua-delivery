package br.com.aguadelivery.order_service.controller;

import br.com.aguadelivery.order_service.dto.CreateOrderRequestDto;
import br.com.aguadelivery.order_service.dto.OrderResponseDto;
import br.com.aguadelivery.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto requestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(requestDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/paid")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersPaid() {
        List<OrderResponseDto> paidOrders = orderService.listPaidOrders();
        if (paidOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paidOrders);
    }

    @GetMapping("/pending-payment")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersPendingPayment() {
        List<OrderResponseDto> pendingPaymentOrders = orderService.listPendingOrders();
        if (pendingPaymentOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pendingPaymentOrders);
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersDelivered() {
        List<OrderResponseDto> deliveredOrders = orderService.listDeliveredOrders();
        if (deliveredOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliveredOrders);
    }

    @GetMapping("/cancelled")
    public ResponseEntity<List<OrderResponseDto>> getCancelledOrders() {
        List<OrderResponseDto> cancelledOrders = orderService.listCancelledOrders();
        if (cancelledOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cancelledOrders);
    }
}
