package br.com.aguadelivery.order_service.mapper;

import br.com.aguadelivery.order_service.dto.OrderItemResponseDto;
import br.com.aguadelivery.order_service.dto.OrderResponseDto;
import br.com.aguadelivery.order_service.model.Order;
import br.com.aguadelivery.order_service.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDto toOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setWhatsappNumber(order.getWhatsappNumber());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        dto.setItems(order.getItems().stream()
                .map(OrderMapper::toOrderItemResponseDto)
                .toList());

        return dto;
    }

    private static OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setProductId(orderItem.getProductId());
        dto.setProductName(orderItem.getProductName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPriceAtTimeOfPurchase(orderItem.getPriceAtTimeOfPurchase());
        dto.setSubtotal(orderItem.getSubtotal());
        return dto;
    }

    public static List<OrderResponseDto> toOrderResponseDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toOrderResponseDto)
                .toList();
    }
}