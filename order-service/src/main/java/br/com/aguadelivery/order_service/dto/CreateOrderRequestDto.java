package br.com.aguadelivery.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDto {

    @NotBlank(message = "Implementar validações adicionais nos DTOs de entrada")
    private String customerName;

    @NotBlank(message = "O número do WhatsApp é obrigatório")
    private String whatsappNumber;

    @NotBlank(message = "O endereço de entrega é obrigatório")
    private String shippingAddress;

    @NotEmpty(message = "A lista de itens do pedido não pode estar vazia")
    @Size(min = 1, message = "Deve haver pelo menos um item no pedido")
    private List<OrderItemRequestDto> items;

    @Data
    public static class OrderItemRequestDto {
        private Long productId;
        private Integer quantity;
    }
}
