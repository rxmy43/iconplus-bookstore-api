package com.ramy.onlinebookstore.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Creating order")
public class CreateOrderRequest {
    @NotEmpty
    @Schema(description = "List of order items")
    private List<OrderItemRequest> orderItems;
}
