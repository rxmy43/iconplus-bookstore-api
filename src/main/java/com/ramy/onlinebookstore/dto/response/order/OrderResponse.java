package com.ramy.onlinebookstore.dto.response.order;

import com.ramy.onlinebookstore.entity.OrderItem;
import com.ramy.onlinebookstore.entity.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Order's response")
public class OrderResponse {
    @Schema(description = "Order id", example = "2")
    private Long orderId;

    @Schema(description = "Status", example = "PAID")
    private OrderStatus status;

    @Schema(description = "Buyer's name", example = "John Doe")
    private String buyerName;

    @Schema(description = "Total Price", example = "500000")
    private BigDecimal totalPrice;

    @Schema(description = "Order item")
    private List<OrderItem> orderItems;
}
