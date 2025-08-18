package br.com.aguadelivery.order_service.controller;

import br.com.aguadelivery.order_service.dto.CreateOrderRequestDto;
import br.com.aguadelivery.order_service.dto.OrderResponseDto;
import br.com.aguadelivery.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
