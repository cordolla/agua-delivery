package br.com.aguadelivery.order_service.dto;

import jakarta.validation.constraints.*;
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

        @NotNull(message = "O ID do produto é obrigatório")
        private Long productId;

        @NotNull(message = "O preço do produto é obrigatório")
        @Min(value = 1, message = "O preço do produto deve ser maior que zero")
        private Integer quantity;
    }
}
