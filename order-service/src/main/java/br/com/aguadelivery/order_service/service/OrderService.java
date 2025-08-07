package br.com.aguadelivery.order_service.service;

import br.com.aguadelivery.order_service.mapper.OrderMapper;
import br.com.aguadelivery.order_service.client.ProductServiceClient;
import br.com.aguadelivery.order_service.dto.CreateOrderRequestDto;
import br.com.aguadelivery.order_service.dto.OrderResponseDto;
import br.com.aguadelivery.order_service.dto.ProductDto;
import br.com.aguadelivery.order_service.model.Order;
import br.com.aguadelivery.order_service.model.OrderItem;
import br.com.aguadelivery.order_service.model.OrderStatus;
import br.com.aguadelivery.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

    public OrderService(OrderRepository orderRepository, ProductServiceClient productServiceClient) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto){
        Order order = new Order();
        order.setCustomerName(requestDto.getCustomerName());
        order.setWhatsappNumber(requestDto.getWhatsappNumber());
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        BigDecimal totalOrderAmount = BigDecimal.ZERO;

        for(CreateOrderRequestDto.OrderItemRequestDto itemRequest : requestDto.getItems()) {
            ProductDto productDetails = productServiceClient.getProductById(itemRequest.getProductId());

            OrderItem orderItem= new OrderItem();
            orderItem.setProductId(productDetails.getId());
            orderItem.setProductName(productDetails.getName());
            orderItem.setPriceAtTimeOfPurchase(productDetails.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());

            BigDecimal subtotal = productDetails.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            orderItem.setSubtotal(subtotal);

            order.addItem(orderItem);
        }

        order.setTotalAmount(totalOrderAmount);

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toOrderResponseDto(savedOrder);
    }
}
